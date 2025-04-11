package EduVoice.Backend.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

@Service
public class VideoProcessor {

    public File extractAudio(Path videoPath) throws IOException, InterruptedException {
        String inputFilePath = videoPath.toString();
        String outputFilePath = inputFilePath.replace(".mp4", ".mp3");

        ProcessBuilder pb = new ProcessBuilder(
                "ffmpeg", "-i", inputFilePath, "-q:a", "0", "-map", "a", outputFilePath
        );

        pb.inheritIO(); // To see FFmpeg output in console (optional)
        Process process = pb.start();
        int exitCode = process.waitFor();

        if (exitCode != 0) {
            throw new RuntimeException("FFmpeg failed to extract audio");
        }

        return new File(outputFilePath);
    }
}
