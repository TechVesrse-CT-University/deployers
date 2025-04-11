package EduVoice.Backend.repository;

import EduVoice.Backend.model.Video;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface VideoRepository  extends MongoRepository <Video,String> {}

