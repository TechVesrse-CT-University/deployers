package EduVoice.Backend.service;

import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Service
public class TTSService {

    public void generateTTS(String text, String lang, String outputPath) throws Exception {
        // Construct the command
        ProcessBuilder pb = new ProcessBuilder("python", "generate_tts.py", text, lang, outputPath);
        pb.redirectErrorStream(true); // Merge stdout and stderr

        Process process = pb.start();

        // Capture output (for logging/debug)
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println("[TTS LOG] " + line);
            }
        }

        int exitCode = process.waitFor();
        if (exitCode != 0) {
            throw new RuntimeException("gTTS generation failed with exit code: " + exitCode);
        }
    }
}
