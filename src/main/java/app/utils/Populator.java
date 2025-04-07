package app.utils;

import app.entities.Instructor;
import app.entities.SkiLesson;
import app.entities.SkillLevel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class Populator {
    private final Logger logger = LoggerFactory.getLogger(Populator.class);

    private Instructor john, jane;
    private SkiLesson beginnerLesson;

    public Populator() {
        // Create objects up front, used for testing the API before being put into production
        john = new Instructor("John", "Doe", "john.doe@example.com", "12345678", 5);
        jane = new Instructor("Jane", "Smith", "jane.smith@example.com", "87654321", 8);

        beginnerLesson = new SkiLesson(
                "Beginner Basics",
                LocalDateTime.now().plusDays(1),
                LocalDateTime.now().plusDays(1).plusHours(2),
                60.3913, 5.3221,
                new BigDecimal("499.99"),
                SkillLevel.BEGINNER,
                john
        );
    }

    public void populate(EntityManagerFactory emf) {
        try (EntityManager em = emf.createEntityManager()) {
            em.getTransaction().begin();

            logger.info("Clearing old data...");
            em.createQuery("DELETE FROM SkiLesson").executeUpdate();
            em.createQuery("DELETE FROM Instructor").executeUpdate();

            logger.info("Persisting mock data...");

            em.persist(john);
            em.persist(jane);
            em.persist(beginnerLesson);

            john = em.merge(john);
            jane = em.merge(jane);
            beginnerLesson = em.merge(beginnerLesson);

            em.getTransaction().commit();
            logger.info("Database populated successfully.");
        } catch (Exception e) {
            logger.error("Error populating database", e);
        }
    }

    public List<SkiLesson> getSkiLessons() {
        return List.of(beginnerLesson);
    }
}
