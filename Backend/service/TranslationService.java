package EduVoice.Backend.service;

import org.json.JSONObject;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class TranslationService {

    private static final String TRANSLATION_API_URL =  "http://localhost:5000/translate"; // More stable mirror

    /**
     * Translates the given English text to the specified target language.
     * Supported targetLang values: hi (Hindi), pa (Punjabi), ta (Tamil), bn (Bengali)
     *
     * @param text       The English text to be translated.
     * @param targetLang The target language code (e.g., "hi" for Hindi).
     * @return The translated text or error message.
     */
    public String translateText(String text, String targetLang) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            Map<String, String> requestBody = new HashMap<>();
            requestBody.put("q", text);
            requestBody.put("source", "en");
            requestBody.put("target", targetLang);
            requestBody.put("format", "text");

            HttpEntity<Map<String, String>> request = new HttpEntity<>(requestBody, headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<String> response = restTemplate.postForEntity(TRANSLATION_API_URL, request, String.class);

            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                JSONObject json = new JSONObject(response.getBody());
                return json.getString("translatedText");
            } else {
                return "Translation failed: API did not return a valid response.";
            }
        } catch (Exception e) {
            return "Translation failed: " + e.getMessage();
        }
    }
}
