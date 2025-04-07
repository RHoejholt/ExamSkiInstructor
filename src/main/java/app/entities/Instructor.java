package app.entities;
import app.persistence.dtos.InstructorDTO;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "instructor")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Instructor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    String firstName;

    String lastName;

    private String email;

    private String phone;

    private int yearsOfExperience;

    @OneToMany(mappedBy = "instructor", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<SkiLesson> lessons;

    public Instructor(InstructorDTO dto) {
        this.id = dto.getId();
        this.firstName = dto.getFirstName();
        this.lastName = dto.getLastName();
        this.email = dto.getEmail();
        this.phone = dto.getPhone();
        this.yearsOfExperience = dto.getYearsOfExperience();
    }

    public Instructor(String firstName, String lastName, String email, String phone, int yearsOfExperience) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
        this.yearsOfExperience = yearsOfExperience;
    }

    public void addLesson(SkiLesson lesson) {
        lessons.add(lesson);
        lesson.setInstructor(this);
    }

    public void removeLesson(SkiLesson lesson) {
        lessons.remove(lesson);
        lesson.setInstructor(null);
    }

}