package guru.qa.homework18;

import org.junit.jupiter.api.*;

import static guru.qa.Endpoints.*;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;
@Tag("APITests")
public class UpdateUserTests {
    String userId;
    @BeforeEach
    void createUser() {
        String data = "{\"name\": \"morpheus\",\"job\": \"leader\"}";

        userId = given()
                .baseUri(BASE_URL)
                .log().uri()
                .log().method()
                .contentType(JSON)
                .body(data)
                .when()
                .post(CREATE_ENDPOINT)
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .extract().path("id");
    }
    @DisplayName("обновить данные созданного пользователя")
    @Test
    void updateUserTest() {
        String data = "{\"name\": \"morpheus\",\"job\": \"manager\"}";

        given()
                .log().uri()
                .log().method()
                .baseUri(BASE_URL)
                .contentType(JSON)
                .body(data)
                .when()
                .put(UPDATE_ENDPOINT, userId)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("manager"));
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
