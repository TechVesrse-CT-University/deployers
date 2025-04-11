package EduVoice.Backend.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("teacher")
@Data
public class Teacher {
@Id
   private  String id;
   private String  name;
   private String qualification;
   private String subject;
   private String schoolorcollege;
}
