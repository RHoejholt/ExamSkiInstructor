package app.config;

import app.controllers.AuthController;
import app.controllers.SkiLessonController;
import app.controllers.InstructorController;
import app.exceptions.ApiException;
import app.exceptions.ApiSecurityException;
import app.exceptions.NotAuthorizedException;
import app.middleware.SecurityMiddleware;
import app.utils.Utils;
import app.utils.JwtUtil;
import io.javalin.Javalin;
import io.javalin.http.Context;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Routes {

    private static Logger logger = LoggerFactory.getLogger(Routes.class);

    public static void addRoutes(Javalin app, SkiLessonController skiLessonController, InstructorController instructorController, AuthController authController) {

        app.get("/skilessons", skiLessonController::getAllSkiLessons);

        app.get("/skilessons/{skilessonId}", skiLessonController::getSkiLessonById);

        app.post("/skilessons", skiLessonController::createSkiLesson);

        app.put("/skilessons/{skilessonId}", skiLessonController::updateSkiLesson);

        app.delete("/skilessons/{skilessonId}", skiLessonController::deleteSkiLesson);

        app.put("/skilessons/{skilessonId}/instructors/{instructorId}", skiLessonController::addInstructorToLesson);

        app.get("/skilesson/instructor/{instructorId}", skiLessonController::getLessonsByInstructor);

        app.get("/skilessons/level/{level}", skiLessonController::getLessonsByLevel);

        app.post("/instructors", instructorController::createInstructor);

        app.get("/instructors", instructorController::getAllInstructors);

        app.put("/instructors/{instructorId}", instructorController::updateInstructor);

        app.delete("/instructors/{instructorId}", instructorController::deleteInstructor);

        app.post("/login", authController::login);

        // Protected endpoints (ADMIN only)
        app.post("/createskilessons",  skiLessonController::createSkiLesson);
        app.put("/updateskilessons/{skilessonId}",  skiLessonController::updateSkiLesson);
        app.delete("/deleteskilesson/{skilessonId}", skiLessonController::deleteSkiLesson);

    }

    //add exceptions
    public static void addExceptions(Javalin app) {
        app.exception(ApiException.class, Routes::apiExceptionHandler);
        app.exception(ApiSecurityException.class, Routes::apiSecurityExceptionHandler);
        app.exception(NotAuthorizedException.class, Routes::apiNotAuthorizedExceptionHandler);
        app.exception(Exception.class, Routes::generalExceptionHandler);
    }

    private static void generalExceptionHandler(@NotNull Exception e, @NotNull Context ctx) {
        logger.error("An unhandled exception occurred", e.getMessage());
        ctx.json(Utils.convertToJsonMessage(ctx, "error", e.getMessage()));
    }

    private static void apiExceptionHandler(@NotNull ApiException e, @NotNull Context ctx) {
        ctx.status(e.getStatusCode());
        logger.warn("An API exception occurred: Code: {}, Message: {}", e.getStatusCode(), e.getMessage());
        ctx.json(Utils.convertToJsonMessage(ctx, "warning", e.getMessage()));
    }

    private static void apiSecurityExceptionHandler(@NotNull ApiSecurityException e, @NotNull Context ctx) {
        ctx.status(e.getCode());
        logger.warn("A Security API exception occurred: Code: {}, Message: {}", e.getCode(), e.getMessage());
        ctx.json(Utils.convertToJsonMessage(ctx, "warning", e.getMessage()));
    }

    private static void apiNotAuthorizedExceptionHandler(@NotNull NotAuthorizedException e, @NotNull Context ctx) {
        ctx.status(e.getStatusCode());
        logger.warn("A Not authorized Security API exception occurred: Code: {}, Message: {}", e.getStatusCode(), e.getMessage());
        ctx.json(Utils.convertToJsonMessage(ctx, "warning", e.getMessage()));
    }


}