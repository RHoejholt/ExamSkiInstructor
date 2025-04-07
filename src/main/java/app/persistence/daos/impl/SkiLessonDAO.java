package app.persistence.daos.impl;

import app.controllers.InstructorController;
import app.entities.Instructor;
import app.persistence.daos.ISkiLessonInstructorDAO;
import app.persistence.dtos.SkiLessonDTO;
import app.entities.SkiLesson;
import app.persistence.daos.IDAO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class SkiLessonDAO implements IDAO<SkiLessonDTO>, ISkiLessonInstructorDAO {
    private static SkiLessonDAO instance;
    private static EntityManagerFactory emf;

    public SkiLessonDAO() {
    }

    @Override
    public void addInstructorToSkiLesson(int lessonId, int instructorId) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            SkiLesson skiLesson = em.find(SkiLesson.class, lessonId);
            if (skiLesson == null) {
                em.getTransaction().rollback();
                throw new IllegalArgumentException("SkiLesson with ID " + lessonId + " not found");
            }

            Instructor instructor = em.find(Instructor.class, instructorId);
            if (instructor == null) {
                em.getTransaction().rollback();
                throw new IllegalArgumentException("Instructor with ID " + instructorId + " not found");
            }

            instructor.addLesson(skiLesson);
            em.merge(skiLesson);

            em.getTransaction().commit();
        }
    }

    @Override
    public Set<SkiLessonDTO> getSkiLessonsByInstructor(int instructorId) {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<SkiLesson> query = em.createQuery(
                    "SELECT s FROM SkiLesson s WHERE s.instructor.id = :instructorId",
                    SkiLesson.class
            );
            query.setParameter("instructorId", instructorId);
            return new java.util.HashSet<>(SkiLessonDTO.toDTOList(query.getResultList()));
        }
    }

    public static SkiLessonDAO getInstance(EntityManagerFactory _emf) {
        if (instance == null) {
            instance = new SkiLessonDAO();
            emf = _emf;
        }
        return instance;
    }

    @Override
    public SkiLessonDTO save(SkiLessonDTO skiLessonDTO) {
        SkiLesson skiLesson = new SkiLesson(skiLessonDTO);
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            em.persist(skiLesson);
            em.getTransaction().commit();
        }
        return new SkiLessonDTO(skiLesson);
    }

    public List<SkiLessonDTO> getAllLessons() {
        try (EntityManager em = emf.createEntityManager()) {
            TypedQuery<SkiLesson> query = em.createQuery("SELECT p FROM SkiLesson p", SkiLesson.class);
            return SkiLessonDTO.toDTOList(query.getResultList());
        }
    }

    @Override
    public Optional<SkiLessonDTO> findById(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            SkiLesson skiLesson = em.find(SkiLesson.class, id);
            if (skiLesson != null) {
                return Optional.of(new SkiLessonDTO(skiLesson));
            }
            return Optional.empty();
        }
    }

    @Override
    public List<SkiLessonDTO> findAll() {
        try (EntityManager em = emf.createEntityManager()){
            TypedQuery<SkiLesson> query = em.createQuery("SELECT e FROM SkiLesson e", SkiLesson.class);
            return SkiLessonDTO.toDTOList(query.getResultList());
        }
    }

    @Override
    public Optional<SkiLessonDTO> update(SkiLessonDTO dto) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            SkiLesson skiLesson = em.find(SkiLesson.class, dto.getId());
            if (skiLesson != null) {
                skiLesson.setName(dto.getName());
                skiLesson.setEndTime(dto.getEndTime());
                skiLesson.setStartTime(dto.getStartTime());
                skiLesson.setLongitude(dto.getLongitude());
                skiLesson.setLatitude(dto.getLatitude());
                skiLesson.setPrice(dto.getPrice());
                skiLesson.setLevel(dto.getLevel());
                if (dto.getInstructorId() != 0) {
                    Instructor instructor = em.find(Instructor.class, dto.getInstructorId());
                    if (instructor == null) {
                        em.getTransaction().rollback();
                        throw new IllegalArgumentException("Instructor with ID " + dto.getInstructorId() + " not found");
                    }
                    skiLesson.setInstructor(instructor);
                }
                skiLesson = em.merge(skiLesson);
                em.getTransaction().commit();
            }
            return Optional.of(new SkiLessonDTO(skiLesson));
        }
    }

    @Override
    public void delete(int id) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();
            SkiLesson skiLesson = em.find(SkiLesson.class, id);
            if (skiLesson != null) {
                em.remove(skiLesson);
                em.getTransaction().commit();
            } else{
                throw new IllegalArgumentException("SkiLesson with ID " + id + " not found");
            }
        }
    }
}
