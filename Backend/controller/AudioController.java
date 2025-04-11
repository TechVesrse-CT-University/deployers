package EduVoice.Backend.controller;

import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Map;

@RestController
@RequestMapping("/api/audio")
public class AudioController {


    @PostMapping("/stt")
    public ResponseEntity<String> speechToText(@RequestParam("file") MultipartFile audioFile) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth("YOUR_OPENAI_API_KEY"); // Replace with your real OpenAI API Key
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("file", new ByteArrayResource(audioFile.getBytes()) {
            @Override
            public String getFilename() {
                return audioFile.getOriginalFilename();
            }
        });
        body.add("model", "whisper-1");
        body.add("language", "hi"); // Hardcoded for now. Can make dynamic.

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://api.openai.com/v1/audio/transcriptions",
                requestEntity,
                Map.class
        );

        if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
            return ResponseEntity.ok((String) response.getBody().get("text"));
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to get transcription");
        }
    }


    @PostMapping("/merge")
    public ResponseEntity<String> mergeAudioAndVideo(
            @RequestParam("videoName") String videoName,
            @RequestParam("languageCode") String languageCode
    ) {
        try {
            ProcessBuilder pb = new ProcessBuilder(
                    "python", "merge_audio_video.py", videoName, languageCode
            );

            // Set working directory to where merge_audio_video.py exists
            pb.directory(new File("C:/Users/PATHANIA/Desktop/Backend/Backend"));
            pb.redirectErrorStream(true); // Combine stderr with stdout

            Process process = pb.start();

            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            StringBuilder output = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode == 0) {
                return ResponseEntity.ok("Merge completed successfully.\n" + output);
            } else {
                return ResponseEntity.status(500).body(" Merge failed with exit code " + exitCode + "\n" + output);
            }

        } catch (Exception e) {
            return ResponseEntity.status(500).body(" Exception: " + e.getMessage());
        }
    }
}
