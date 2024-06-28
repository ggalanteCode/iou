package org.ggalantecode;

import io.quarkus.test.InjectMock;
import io.quarkus.test.junit.QuarkusTest;
import org.ggalantecode.service.IouService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
class IouResourceTest {

    @InjectMock
    IouService iouService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void createUser() {

    }

    @Test
    void readUserInformation() {
        given()
                .when().get("/users")
                .then()
                .statusCode(200);
                //.body(is("Hello from RESTEasy Reactive"));
    }

    @Test
    void createIou() {
    }

}