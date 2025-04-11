package EduVoice.Backend.service;

import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class WhisperService {

    public String transcribeWithWhisper(String videoPath) throws IOException {
        String pythonExe = "C:/Users/PATHANIA/AppData/Local/Programs/Python/Python310/python.exe";
        String scriptPath = "C:/Users/PATHANIA/Desktop/Backend/Backend/whisper_transcribe.py";

        ProcessBuilder pb = new ProcessBuilder(pythonExe, scriptPath, videoPath);
        pb.redirectErrorStream(true);
        Process process = pb.start();

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        StringBuilder output = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            output.append(line).append("\n");
        }

        try {
            int exitCode = process.waitFor();
            output.append("\nExit Code: ").append(exitCode);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return output.toString().trim();
    }
}