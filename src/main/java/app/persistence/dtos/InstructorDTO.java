package app.persistence.dtos;
import app.entities.Instructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class InstructorDTO {

    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private int yearsOfExperience;

    public InstructorDTO(Instructor instructor) {
        this.id = instructor.getId();
        this.firstName = instructor.getFirstName();
        this.lastName = instructor.getLastName();
        this.email = instructor.getEmail();
        this.phone = instructor.getPhone();
        this.yearsOfExperience = instructor.getYearsOfExperience();
    }


    public Instructor toEntity() {
        Instructor instructor = new Instructor();
        instructor.setId(this.id);
        instructor.setFirstName(this.firstName);
        instructor.setLastName(this.lastName);
        instructor.setEmail(this.email);
        instructor.setPhone(this.phone);
        instructor.setYearsOfExperience(this.yearsOfExperience);
        return instructor;
    }


    public static List<InstructorDTO> toDTOList(List<Instructor> instructors){
            return instructors.stream().map(InstructorDTO::new).toList();
        }
}