package EduVoice.Backend.service;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String geminiApiKey;

    public String askGemini(String question, String context) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("x-goog-api-key", geminiApiKey);

            Map<String, Object> message = new HashMap<>();
            message.put("role", "user");
            message.put("parts", List.of(Map.of("text", "Context: " + context + "\n\nQuestion: " + question)));

            Map<String, Object> payload = new HashMap<>();
            payload.put("contents", List.of(message));

            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(payload, headers);

            ResponseEntity<String> response = restTemplate.postForEntity(
                    "https://generativelanguage.googleapis.com/v1beta/models/gemini-pro:generateContent",
                    entity,
                    String.class
            );

            JSONObject json = new JSONObject(response.getBody());
            return json
                    .getJSONArray("candidates")
                    .getJSONObject(0)
                    .getJSONObject("content")
                    .getJSONArray("parts")
                    .getJSONObject(0)
                    .getString("text");

        } catch (Exception e) {
            return "Gemini AI failed to respond: " + e.getMessage();
        }
    }
}
