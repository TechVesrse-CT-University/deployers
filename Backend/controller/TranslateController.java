//package EduVoice.Backend.controller;//package EduVoice.Backend.controller;
////
////import lombok.RequiredArgsConstructor;
////import org.springframework.core.io.ByteArrayResource;
////import org.springframework.http.*;
////import org.springframework.util.LinkedMultiValueMap;
////import org.springframework.util.MultiValueMap;
////import org.springframework.web.bind.annotation.*;
////import org.springframework.web.client.RestTemplate;
////import org.springframework.web.multipart.MultipartFile;
////
////import java.io.IOException;
////import java.util.Map;
////
////@RestController
////@RequestMapping("/api/ai")
////@RequiredArgsConstructor
////public class TranslateController {
////
////    private final RestTemplate restTemplate = new RestTemplate();
////
////    // 1. Speech to Text using OpenAI Whisper
////    @PostMapping("/stt")
////    public ResponseEntity<String> speechToText(@RequestParam("file") MultipartFile audioFile) throws IOException {
////        HttpHeaders headers = new HttpHeaders();
////        headers.setBearerAuth("YOUR_OPENAI_API_KEY");
////        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
////
////        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
////        body.add("file", new ByteArrayResource(audioFile.getBytes()) {
////            @Override
////            public String getFilename() {
////                return audioFile.getOriginalFilename();
////            }
////        });
////        body.add("model", "whisper-1");
////        body.add("language", "hi"); // Change to "pa", "ta", or "bn"
////
////        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
////        ResponseEntity<Map> response = restTemplate.postForEntity("https://api.openai.com/v1/audio/transcriptions", requestEntity, Map.class);
////
////        return ResponseEntity.ok((String) response.getBody().get("text"));
////    }
////
////    // 2. Translate Text using Bhashini
////    @PostMapping("/translate")
////    public ResponseEntity<String> translateText(
////            @RequestParam("text") String text,
////            @RequestParam("sourceLang") String sourceLang,
////            @RequestParam("targetLang") String targetLang
////    ) {
////        HttpHeaders headers = new HttpHeaders();
////        headers.setContentType(MediaType.APPLICATION_JSON);
////        // headers.set("Authorization", "Bearer YOUR_BHASHINI_API_KEY");
////
////        Map<String, Object> requestBody = Map.of(
////                "sourceLanguage", sourceLang,
////                "targetLanguage", targetLang,
////                "text", text
////        );
////
////        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
////        ResponseEntity<Map> response = restTemplate.postForEntity("https://dhruva-api.bhashini.gov.in/translate", entity, Map.class);
////
////        String translated = (String) response.getBody().get("translatedText");
////        return ResponseEntity.ok(translated);
////    }
////
////    // 3. Text to Speech using Bhashini
////    @PostMapping("/tts")
////    public ResponseEntity<byte[]> textToSpeech(
////            @RequestParam("text") String text,
////            @RequestParam("lang") String lang
////    ) {
////        HttpHeaders headers = new HttpHeaders();
////        headers.setContentType(MediaType.APPLICATION_JSON);
////        // headers.set("Authorization", "Bearer YOUR_BHASHINI_API_KEY");
////
////        Map<String, Object> body = Map.of(
////                "language", lang,
////                "text", text,
////                "gender", "female" // or "male"
////        );
////
////        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(body, headers);
////        ResponseEntity<byte[]> response = restTemplate.postForEntity("https://dhruva-api.bhashini.gov.in/tts", entity, byte[].class);
////
////        return ResponseEntity.ok()
////                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=output.mp3")
////                .contentType(MediaType.APPLICATION_OCTET_STREAM)
////                .body(response.getBody());
////    }
////}
//
//
//import EduVoice.Backend.service.WhisperService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.core.io.InputStreamResource;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.IOException;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/api/ai")
//public class TranslateController {
//
//    private final WhisperService whisperService;
//    private final LibreTranslateService libreTranslateService;
//    private final GttsTtsService gttsTtsService;
//
//    // 1. Whisper Speech to Text
//    @PostMapping("/stt")
//    public ResponseEntity<String> speechToText(@RequestParam("file") MultipartFile audioFile) throws IOException {
//        File tempFile = File.createTempFile("audio", ".mp3");
//        audioFile.transferTo(tempFile);
//
//        String transcript = whisperService.transcribeAudio(tempFile);
//        return ResponseEntity.ok(transcript);
//    }
//
//    // 2. LibreTranslate Text Translation
//    @PostMapping("/translate")
//    public ResponseEntity<String> translateText(
//            @RequestParam("text") String text,
//            @RequestParam("sourceLang") String sourceLang,
//            @RequestParam("targetLang") String targetLang
//    ) {
//        String translated = libreTranslateService.translateText(text, sourceLang, targetLang);
//        return ResponseEntity.ok(translated);
//    }
//
//    // 3. gTTS Text to Speech
//    @PostMapping("/tts")
//    public ResponseEntity<InputStreamResource> textToSpeech(
//            @RequestParam("text") String text,
//            @RequestParam("lang") String lang
//    ) throws Exception {
//        File audioFile = gttsTtsService.generateSpeech(text, lang);
//        InputStreamResource resource = new InputStreamResource(new FileInputStream(audioFile));
//
//        return ResponseEntity.ok()
//                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + audioFile.getName())
//                .contentType(MediaType.APPLICATION_OCTET_STREAM)
//                .body(resource);
//    }
//}
