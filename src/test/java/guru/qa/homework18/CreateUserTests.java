package guru.qa.homework18;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static guru.qa.Endpoints.*;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

@Tag("APITests")
public class CreateUserTests {
    String userId;
    @DisplayName("создать нового пользователя")
    @Test
    void createUserTest() {
        String data = "{\"name\": \"morpheus\",\"job\": \"leader\"}";

        userId = given()
                .log().uri()
                .log().method()
                .baseUri(BASE_URL)
                .contentType(JSON)
                .body(data)
                .when()
                .post(CREATE_ENDPOINT)
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .extract().path("id");
    }

    @AfterEach
    void tearDown() {
        given()
                .log().uri()
                .log().method()
                .baseUri(BASE_URL)
                .when()
                .delete(DELETE_ENDPOINT, userId)
                .then()
                .statusCode(204);
    }
}
