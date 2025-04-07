package app.entities;
import app.persistence.dtos.SkiLessonDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "skilesson")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SkiLesson {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    private String name;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private double latitude;

    private double longitude;

    private BigDecimal price;

    @Enumerated(EnumType.STRING)
    private SkillLevel level;


    @ManyToOne
    @JoinColumn(name = "instructor_id")
    @JsonManagedReference
    private Instructor instructor;

    public SkiLesson(SkiLessonDTO dto) {
        this.id = dto.getId();
        this.name = dto.getName();
        this.startTime = dto.getStartTime();
        this.endTime = dto.getEndTime();
        this.latitude = dto.getLatitude();
        this.longitude = dto.getLongitude();
        this.price = dto.getPrice();
        this.level = dto.getLevel();
        // Instructor is not included here – should be set separately or via another constructor
    }


    public SkiLesson(String name, LocalDateTime startTime, LocalDateTime endTime,
                     double latitude, double longitude, BigDecimal price, SkillLevel level, Instructor instructor) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.latitude = latitude;
        this.longitude = longitude;
        this.price = price;
        this.level = level;
        this.instructor = instructor;
    }




}