package EduVoice.Backend.repository;

import EduVoice.Backend.model.Teacher;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface TeacherRepository  extends MongoRepository<Teacher,String> {}

