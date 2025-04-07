package app.persistence.daos.impl;

import app.persistence.dtos.InstructorDTO;
import app.entities.Instructor;
import app.persistence.daos.IDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

public class InstructorDAO implements IDAO<InstructorDTO> {
    private static InstructorDAO instance;
    private static EntityManagerFactory emf;

    private InstructorDAO() {
    }

    public static InstructorDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            instance = new InstructorDAO();
            emf = _emf;
        }
        return instance;
    }

    @Override
    public InstructorDTO save(InstructorDTO instructorDTO) {
        Instructor instructor = new Instructor(instructorDTO);
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(instructor);
            em.getTransaction().commit();
        }
        return new InstructorDTO(instructor); // Return a DTO representing the saved entity
    }

    public List<InstructorDTO> getInstructors() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<Instructor> query = em.createQuery("SELECT p FROM Instructor p", Instructor.class);
            return InstructorDTO.toDTOList(query.getResultList()); // Convert the list of Entityies to DTOs
        }
    }

    @Override
    public Optional<InstructorDTO> findById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            Instructor instructor = em.find(Instructor.class, id);
            if (instructor != null) {
                return Optional.of(new InstructorDTO(instructor)); // Wrap the found entity in a DTO
            }
            return Optional.empty(); // Return empty if no entity is found
        }
    }

    @Override
    public List<InstructorDTO> findAll() {
        try (EntityManager em = emf.createEntityManager()){
            TypedQuery<Instructor> query = em.createQuery("SELECT e FROM Instructor e", Instructor.class);
            return InstructorDTO.toDTOList(query.getResultList()); // Convert all entities to DTOs
        }
    }

    @Override
    public Optional<InstructorDTO> update(InstructorDTO dto) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Instructor instructor = em.find(Instructor.class, dto.getId());
            if (instructor != null) {
                instructor.setFirstName(dto.getFirstName());
                instructor.setLastName(dto.getLastName());
                instructor.setEmail(dto.getEmail());
                instructor.setPhone(dto.getPhone());
                instructor.setLessons(dto.toEntity().getLessons());
                instructor.setYearsOfExperience(dto.getYearsOfExperience());

                instructor = em.merge(instructor); // Merge the updated entity back into the persistence context
                em.getTransaction().commit();
            }
            return Optional.of(new InstructorDTO(instructor)); // Return updated entity as DTO
        }
    }

    @Override
    public void delete(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            Instructor instructor = em.find(Instructor.class, id);
            if (instructor != null) {
                em.remove(instructor);
                em.getTransaction().commit();
            }
        }
    }
}
