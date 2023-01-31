package guru.qa.homework18;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static guru.qa.Endpoints.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;

@Tag("APITests")
public class GetUserTests {
    @DisplayName("получить пользователя по id (позитивный сценарий)")
    @Test
    void getUserByIdPositiveTest() {
        int userId = 8;

        int id = given()
                .log().uri()
                .baseUri(BASE_URL)
                .when()
                .get(GET_USER_ENDPOINT, userId)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("data.last_name", is("Ferguson"))
                .extract().path("data.id");

        assertEquals(userId, id);

    }
    @DisplayName("получить пользователя по несуществующему id (негативный сценарий)")
    @Test
    void getUserByIdNegativeTest() {
        int userId = 0;

        given()
                .log().uri()
                .when()
                .get(BASE_URL+GET_USER_ENDPOINT, userId)
                .then()
                .log().status()
                .log().body()
                .statusCode(404);
    }
}
