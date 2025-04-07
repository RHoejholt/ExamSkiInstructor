package app.controllers;
import java.util.*;
import app.config.HibernateConfig;
import app.exceptions.ApiException;
import app.persistence.daos.impl.InstructorDAO;
import app.persistence.dtos.InstructorDTO;
import io.javalin.http.Context;
import jakarta.persistence.EntityManagerFactory;

public class InstructorController {
    private final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
    InstructorDAO instructorDAO = InstructorDAO.getInstance(emf);

    public void getAllInstructors(Context ctx) {
        ctx.json(instructorDAO.getInstructors());
    }

    public void getInstructorById(Context ctx) {
        Optional<InstructorDTO> match = instructorDAO.findById(Integer.parseInt(ctx.pathParam("instructorId")));
        if (match.isPresent()) {
            ctx.json(match.get());
            return;
        } else {
            throw new ApiException(404, "No content found for this request");
        }
    }


    public void createInstructor(Context ctx) throws Exception {
        InstructorDTO instructorDTO = ctx.bodyAsClass(InstructorDTO.class);
        instructorDAO.save(instructorDTO);
        ctx.status(201).json("Instructor added");
    }

    public void updateInstructor(Context ctx) throws Exception {
        int id = Integer.parseInt(ctx.pathParam("instructorId"));
        InstructorDTO instructorDTO = ctx.bodyAsClass(InstructorDTO.class);
        instructorDTO.setId(id);
        Optional<InstructorDTO> updatedEntity = instructorDAO.update(instructorDTO);
        if (updatedEntity.isPresent()) {
            ctx.json("Instructor updated");
        } else {
            ctx.status(404).json("Instructor update failed");
        }
    }

    public void deleteInstructor(Context ctx) throws Exception {
        int id = Integer.parseInt(ctx.pathParam("instructorId"));
        instructorDAO.delete(id);
        ctx.json("Instructor deleted");
    }
}
