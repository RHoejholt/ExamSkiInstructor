package app.config;

import app.Main;
import app.entities.SkiLesson;
import app.utils.Populator;
import io.restassured.RestAssured;
import jakarta.persistence.EntityManagerFactory;
import org.junit.jupiter.api.*;

import static org.hamcrest.Matchers.equalTo;


import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.fail;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class RoutesTest {

    private static final EntityManagerFactory emf = HibernateConfig.getEntityManagerFactoryForTest();
    private SkiLesson skiLesson;

    @BeforeAll
    void setUpAll() {
        int port = 7078;
        Main.setupJavalin(port);
        RestAssured.baseURI = "http://localhost:" + port;

    }

    @BeforeEach
    void setUp() {
        Populator populator = new Populator();
        populator.populate(emf);
        skiLesson = populator.getSkiLessons().get(0);
    }

    @Test
    void getAll() {
        given()
                .when()
                .get("/skilessons")
                .then()
                .statusCode(200)
                .body("size()", equalTo(1));
    }


    @Test
    void getById() {
        given()
                .when()
                .get("/skilessons/" + skiLesson.getId())
                .then()
                .statusCode(200)
                .body("name", equalTo(skiLesson.getName()));
    }
}
