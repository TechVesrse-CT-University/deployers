
package EduVoice.Backend.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class MergeService {

    public void mergeAudioAndVideo(String videoPath, String audioPath, String outputPath) {
        mergeAudioAndVideoPython(videoPath, audioPath, outputPath);
    }


    public void mergeAudioAndVideoPython(String videoPath, String audioPath, String outputPath) {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "python", "scripts/merge_audio_video.py", videoPath, audioPath, outputPath
            );
            pb.redirectErrorStream(true);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("[FFmpeg-Python] " + line);
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new RuntimeException(" Python FFmpeg merge failed.");
            }

            System.out.println("✅ Python FFmpeg merge completed!");

        } catch (Exception e) {
            throw new RuntimeException("⚠️ Error during merge (Python): " + e.getMessage(), e);
        }
    }


    public void generateAudioWithGTTS(String text, String lang, String outputPath, String voiceStyle) {
        try {
            // Save the text to a temporary file
            File tempTextFile = File.createTempFile("tts_text_", ".txt");
            java.nio.file.Files.write(tempTextFile.toPath(), text.getBytes(java.nio.charset.StandardCharsets.UTF_8));

            List<String> command = new ArrayList<>();
            command.add("python");
            command.add("scripts/generate_audio.py");
            command.add(tempTextFile.getAbsolutePath());
            command.add(lang);
            command.add(outputPath);

            System.out.println(" Generating audio from file: " + tempTextFile.getAbsolutePath());

            ProcessBuilder pb = new ProcessBuilder(command);
            pb.redirectErrorStream(true);
            Process process = pb.start();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("[gTTS] " + line);
            }

            int exitCode = process.waitFor();
            tempTextFile.delete(); // Clean up

            if (exitCode != 0) {
                throw new RuntimeException(" gTTS failed with exit code: " + exitCode);
            }

            System.out.println(" gTTS audio generated: " + outputPath);

        } catch (Exception e) {
            throw new RuntimeException(" gTTS generation error: " + e.getMessage(), e);
        }
    }
}
