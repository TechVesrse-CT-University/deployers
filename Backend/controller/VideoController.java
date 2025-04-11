
package EduVoice.Backend.controller;

import EduVoice.Backend.model.Teacher;
import EduVoice.Backend.model.Video;
import EduVoice.Backend.repository.TeacherRepository;
import EduVoice.Backend.repository.VideoRepository;
import EduVoice.Backend.service.*;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api/video")
public class VideoController {

    private static final String UPLOAD_DIR = "uploads/";
    private static final String AUDIO_DIR = "translated_audio/";
    private static final String VIDEO_OUT_DIR = "translated_video/";

    @Autowired
    private VideoProcessingService videoProcessingService;
    @Autowired
    private VideoRepository videoRepository;

    @Autowired
    private TeacherRepository teacherRepository;

    @Autowired
    private WhisperService whisperService;

    @Autowired
    private TranslationService translationService;

    @Autowired
    private MergeService mergeService;

    @Autowired
    private GeminiService geminiService;

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadVideo(@RequestParam("file") MultipartFile file,
                                                           @RequestParam("teacherId") String teacherId,
                                                           @RequestParam(required = false) String lang) throws IOException, InterruptedException {
        Path path = Paths.get(UPLOAD_DIR + file.getOriginalFilename());
        Files.createDirectories(path.getParent());
        Files.write(path, file.getBytes());

        String fullPath = path.toAbsolutePath().toString();
        String transcript = whisperService.transcribeWithWhisper(fullPath);

        Video video = new Video();
        video.setFileName(file.getOriginalFilename());
        video.setFileType(FilenameUtils.getExtension(file.getOriginalFilename()));
        video.setTeacherId(teacherId);
        video.setUploadTime(LocalDateTime.now());
        video.setTranscript(transcript);

        videoRepository.save(video);

        if (lang != null && !lang.isEmpty()) {
            String translated = translationService.translateText(transcript, lang);
            String audioPath = AUDIO_DIR + video.getId() + "_" + lang + ".mp3";
            mergeService.generateAudioWithGTTS(translated, lang, audioPath, "neutral");
        }

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Video uploaded and transcribed successfully!");
        response.put("transcript", transcript);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/videos")
    public List<Map<String, Object>> getAllVideos() {
        List<Video> videos = videoRepository.findAll();
        List<Map<String, Object>> result = new ArrayList<>();

        for (Video video : videos) {
            Map<String, Object> map = new HashMap<>();
            map.put("video", video);
            Teacher teacher = teacherRepository.findById(video.getTeacherId()).orElse(null);
            map.put("teacher", teacher);
            result.add(map);
        }
        return result;
    }

    @PostMapping("/transcribe")
    public ResponseEntity<String> transcribeManually(@RequestParam("filePath") String filePath) {
        try {
            String transcript = whisperService.transcribeWithWhisper(filePath);
            return ResponseEntity.ok(transcript);
        } catch (IOException e) {
            return ResponseEntity.status(500).body("Error during transcription: " + e.getMessage());
        }
    }

    @PostMapping("/translate")
    public ResponseEntity<String> translate(@RequestParam String text, @RequestParam String lang) {
        String translated = translationService.translateText(text, lang);
        return ResponseEntity.ok(translated);
    }

    @PostMapping("/tts/{videoId}")
    public ResponseEntity<String> generateAudio(
            @PathVariable String videoId,
            @RequestParam String lang,
            @RequestParam(required = false, defaultValue = "neutral") String voiceStyle) {
        try {
            List<String> supportedLangs = Arrays.asList("bn", "ml", "kn", "gu", "te", "ta");

            if (!supportedLangs.contains(lang)) {
                return ResponseEntity.badRequest()
                        .body(" gTTS does not support the selected language: " + lang);
            }

            Video video = videoRepository.findById(videoId).orElseThrow();
            String transcript = video.getTranscript();
            String translated = translationService.translateText(transcript, lang);

            String outputPath = AUDIO_DIR + videoId + "_" + lang + "_" + voiceStyle + ".mp3";
            mergeService.generateAudioWithGTTS(translated, lang, outputPath, voiceStyle);

            return ResponseEntity.ok(" TTS generated with voice style: " + voiceStyle + "\nSaved at: " + outputPath);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(" Error generating TTS: " + e.getMessage());
        }
    }

    @GetMapping("/stream/video/{fileName}")
    public ResponseEntity<Resource> streamOriginal(@PathVariable String fileName) throws IOException {
        return streamFile(UPLOAD_DIR, fileName);
    }

    @GetMapping("/stream/audio/{fileName}")
    public ResponseEntity<Resource> streamTTS(@PathVariable String fileName) throws IOException {
        return streamFile(AUDIO_DIR, fileName);
    }

    @GetMapping("/stream/{fileName}")
    public ResponseEntity<Resource> streamVideo(@PathVariable String fileName) throws IOException {
        return streamFile(UPLOAD_DIR, fileName);
    }

    private ResponseEntity<Resource> streamFile(String directory, String fileName) throws IOException {
        fileName = fileName.trim();
        Path filePath = Paths.get(directory).resolve(fileName).normalize();

        System.out.println("Trying to stream file: " + filePath.toAbsolutePath());

        if (!Files.exists(filePath)) {
            return ResponseEntity.notFound().build();
        }

        Resource resource = new UrlResource(filePath.toUri());
        String contentType = Files.probeContentType(filePath);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "inline; filename=\"" + resource.getFilename() + "\"")
                .contentType(MediaType.parseMediaType(contentType != null ? contentType : "application/octet-stream"))
                .body(resource);
    }

    @PostMapping("/doubt")
    public ResponseEntity<String> askDoubt(@RequestParam String videoId, @RequestParam String question) {
        try {
            Video video = videoRepository.findById(videoId).orElseThrow();
            String answer = geminiService.askGemini(question, video.getTranscript());
            return ResponseEntity.ok(answer);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error answering doubt: " + e.getMessage());
        }
    }

    @PostMapping("/download/translated/{videoId}")
    public ResponseEntity<String> generateTranslatedVideo(@PathVariable String videoId, @RequestParam String lang) {
        try {
            Video video = videoRepository.findById(videoId).orElseThrow();
            String transcript = video.getTranscript();
            String translated = translationService.translateText(transcript, lang);

            String audioPath = AUDIO_DIR + videoId + "_" + lang + ".mp3";
            mergeService.generateAudioWithGTTS(translated, lang, audioPath, "neutral");

            String videoPath = UPLOAD_DIR + video.getFileName();
            String outputPath = VIDEO_OUT_DIR + videoId + "_" + lang + ".mp4";

            new File(VIDEO_OUT_DIR).mkdirs();

            mergeService.mergeAudioAndVideo(videoPath, audioPath, outputPath);

            return ResponseEntity.ok("Translated video created successfully. Saved at: " + outputPath);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("Error during translated video creation: " + e.getMessage());
        }
    }

    @GetMapping("/download/translated/{fileName}")
    public ResponseEntity<Resource> downloadTranslatedVideo(@PathVariable String fileName) throws IOException {
        return streamFile(VIDEO_OUT_DIR, fileName);
    }

    @PostMapping("/merge")
    public ResponseEntity<String> mergeTranslatedAudio(@RequestParam String videoId, @RequestParam String lang) {
        try {
            Video video = videoRepository.findById(videoId)
                    .orElseThrow(() -> new RuntimeException(" Video not found with ID: " + videoId));

            String videoPath = UPLOAD_DIR + video.getFileName();
            String audioPath = AUDIO_DIR + videoId + "_" + lang + ".mp3";
            String outputPath = VIDEO_OUT_DIR + videoId + "_" + lang + ".mp4";

            new File(AUDIO_DIR).mkdirs();
            new File(VIDEO_OUT_DIR).mkdirs();

            if (!new File(videoPath).exists()) {
                return ResponseEntity.status(404).body(" Original video file not found: " + videoPath);
            }
            if (!new File(audioPath).exists()) {
                return ResponseEntity.status(404).body(" Translated audio file not found: " + audioPath);
            }

            mergeService.mergeAudioAndVideo(videoPath, audioPath, outputPath);

            return ResponseEntity.ok(" Merged video saved at: " + outputPath);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Merge failed: " + e.getMessage());
        }
    }

    @PostMapping("/upload-and-translate")
    public ResponseEntity<String> uploadAndTranslate(
            @RequestParam("file") MultipartFile file,
            @RequestParam("language") String targetLang,
            @RequestParam(value = "voiceStyle", defaultValue = "neutral") String voiceStyle
    ) {

    try {

            String uploadDirPath = "C:/Users/PATHANIA/Desktop/Backend/Backend/upload";
            File uploadDir = new File(uploadDirPath);
            if (!uploadDir.exists()) uploadDir.mkdirs();


            String originalFilename = file.getOriginalFilename();
            String uniqueFileName = UUID.randomUUID() + "_" + originalFilename;


            File destinationFile = new File(uploadDir, uniqueFileName);
            file.transferTo(destinationFile); // 💾 Save here!


            String processedVideoPath = videoProcessingService.processUploadedVideo(
                    destinationFile.getAbsolutePath(), targetLang, voiceStyle
            );

            return ResponseEntity.ok("Processed: " + processedVideoPath);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(" Error: " + e.getMessage());
        }
    }

}


