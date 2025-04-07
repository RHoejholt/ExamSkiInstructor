package app.persistence.dtos;
import app.entities.Instructor;
import app.entities.SkiLesson;
import app.entities.SkillLevel;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class SkiLessonDTO {


    private int id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private double latitude;
    private double longitude;
    private BigDecimal price;
    private SkillLevel level;
    private int instructorId;


    public SkiLessonDTO(SkiLesson skiLesson){
        this.id = skiLesson.getId();
        this.name = skiLesson.getName();
        this.startTime = skiLesson.getStartTime();
        this.endTime = skiLesson.getEndTime();
        this.latitude = skiLesson.getLatitude();
        this.longitude = skiLesson.getLongitude();
        this.price = skiLesson.getPrice();
        this.level = skiLesson.getLevel();
        this.instructorId = skiLesson.getInstructor() != null ? skiLesson.getInstructor().getId() : 0;
    }



    public SkiLesson toEntity(Instructor instructor) {
        SkiLesson lesson = new SkiLesson();
        lesson.setId(this.id);
        lesson.setName(this.name);
        lesson.setStartTime(this.startTime);
        lesson.setEndTime(this.endTime);
        lesson.setLatitude(this.latitude);
        lesson.setLongitude(this.longitude);
        lesson.setPrice(this.price);
        lesson.setLevel(this.level);
        lesson.setInstructor(instructor); // Injected manually
        return lesson;
    }


    public static List<SkiLessonDTO> toDTOList(List<SkiLesson> skiLessons){
        return skiLessons.stream().map(SkiLessonDTO::new).toList();
    }
}
