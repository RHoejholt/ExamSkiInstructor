package app.persistence.daos;

import app.persistence.dtos.SkiLessonDTO;
import java.util.Set;

public interface ISkiLessonInstructorDAO {
    void addInstructorToSkiLesson(int lessonId, int instructorId);
    Set<SkiLessonDTO> getSkiLessonsByInstructor(int instructorId);
}
