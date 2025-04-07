package app;
import app.config.HibernateConfig;
import app.config.Routes;
import app.controllers.AuthController;
import app.persistence.daos.impl.InstructorDAO;
import app.persistence.daos.impl.SkiLessonDAO;
import app.controllers.SkiLessonController;
import app.controllers.InstructorController;
import app.utils.Populator;
import io.javalin.Javalin;
import jakarta.persistence.*;

public class Main {
    public static void main(String[] args) {
        setupJavalin(7000);
    }

    public static void setupJavalin(int port) {
        EntityManagerFactory emf = HibernateConfig.getEntityManagerFactory();
        Javalin app = Javalin.create().start(port);

        SkiLessonController skiLessonController = new SkiLessonController();
        SkiLessonDAO skiLessonDAO = SkiLessonDAO.getInstance(emf);
        InstructorController instructorController = new InstructorController();
        InstructorDAO instructorDAO = InstructorDAO.getInstance(emf);
        AuthController authController = new AuthController();
        Populator populator = new Populator();

        populator.populate(emf);
        Routes.addRoutes(app, skiLessonController, instructorController, authController);
        Routes.addExceptions(app);
    }

}
