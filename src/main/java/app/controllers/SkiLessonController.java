package app.controllers;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import app.middleware.SecurityMiddleware;
import app.config.HibernateConfig;
import app.entities.SkillLevel;
import app.exceptions.ApiException;
import app.persistence.daos.impl.SkiLessonDAO;
import app.persistence.dtos.SkiLessonDTO;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

public class SkiLessonController {
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    SkiLessonDAO skiLessonDAO = SkiLessonDAO.getInstance(emf);


    public void getLessonsByLevel(Context ctx) {
        String levelParam = ctx.pathParam("level").toUpperCase();
        try {
            SkillLevel level = SkillLevel.valueOf(levelParam);
            List<SkiLessonDTO> lessonsByLevel = skiLessonDAO.getAllLessons()
                    .stream()
                    .filter(lesson -> lesson.getLevel() == level)
                    .toList();
            ctx.json(lessonsByLevel);
        } catch (IllegalArgumentException e) {
            throw new ApiException(400, "Invalid Skill Level");
        }
    }

    public void getTotalPricePerInstructor(Context ctx) {
        List<SkiLessonDTO> allLessons = skiLessonDAO.getAllLessons();

        var result = allLessons.stream()
                .filter(lesson -> lesson.getInstructorId() != 0)
                .collect(Collectors.groupingBy(
                        SkiLessonDTO::getInstructorId,
                        Collectors.mapping(SkiLessonDTO::getPrice,
                                Collectors.reducing(BigDecimal.ZERO, BigDecimal::add)
                        )
                ))
                .entrySet()
                .stream()
                .map(entry -> Map.of(
                        "instructorId", entry.getKey(),
                        "totalPrice", entry.getValue()
                ))
                .toList();

        ctx.json(result);
    }


    public void addInstructorToLesson(Context ctx) {
        int lessonId = Integer.parseInt(ctx.pathParam("skilessonId"));
        int instructorId = Integer.parseInt(ctx.pathParam("instructorId"));
        skiLessonDAO.addInstructorToSkiLesson(lessonId, instructorId);
        ctx.status(200).json("Instructor added to SkiLesson");
    }

    public void getLessonsByInstructor(Context ctx) {
        int instructorId = Integer.parseInt(ctx.pathParam("instructorId"));
        Set<SkiLessonDTO> lessons = skiLessonDAO.getSkiLessonsByInstructor(instructorId);
        ctx.json(lessons);
    }


    public void getAllSkiLessons(Context ctx) {
        List<SkiLessonDTO> skiLessons = skiLessonDAO.getAllLessons();
        ctx.json(skiLessons);
    }

    public void getSkiLessonById(Context ctx) {
        Optional<SkiLessonDTO> match = skiLessonDAO.findById(Integer.parseInt(ctx.pathParam("skilessonId")));
        if (match.isPresent()) {
            ctx.json(match.get());
            return;
        } else {
            throw new ApiException(404, "No content found for this request");
        }
    }

    public void createSkiLesson(Context ctx) throws Exception {
        new SecurityMiddleware().handleAdmin(ctx); // Protect endpoint
        SkiLessonDTO skiLessonDTO = ctx.bodyAsClass(SkiLessonDTO.class);
        skiLessonDAO.save(skiLessonDTO);
        ctx.status(201).json("SkiLesson added");
    }

    public void updateSkiLesson(Context ctx) throws Exception {
        new SecurityMiddleware().handleAdmin(ctx);
        int id = Integer.parseInt(ctx.pathParam("skilessonId"));
        SkiLessonDTO skiLessonDTO = ctx.bodyAsClass(SkiLessonDTO.class);
        skiLessonDTO.setId(id);
        Optional<SkiLessonDTO> updatedEntity = skiLessonDAO.update(skiLessonDTO);
        if (updatedEntity.isPresent()) {
            ctx.json("SkiLesson updated");
        } else {
            ctx.status(404).json("SkiLesson update failed");
        }
    }

    public void deleteSkiLesson(Context ctx) throws Exception {
        new SecurityMiddleware().handleAdmin(ctx);
        int id = Integer.parseInt(ctx.pathParam("skilessonId"));
        skiLessonDAO.delete(id);
        ctx.json("SkiLesson deleted");
    }
}