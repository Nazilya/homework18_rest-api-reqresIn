package guru.qa.homework18;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static guru.qa.Endpoints.*;
import static guru.qa.Endpoints.BASE_URL;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;
import static org.hamcrest.Matchers.is;
@Tag("APITests")
public class GetListUsersTests {
    String usersEmail = "michael.lawson@reqres.in";
    @DisplayName("получить список всех пользователей (позитивный сценарий)")
    @Test
    void getListUsersByPageNumberPositiveTest() {
        given()
                .log().uri()
                .baseUri(BASE_URL)
                .when()
                .param("page", 2)
                .get(GET_USERS_LIST_ENDPOINT)
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("page", is(2))
                .body("data.id[0]", is(7))
                .body("data.findAll{it.id == 7}.last_name.flatten()",
                        hasItem("Lawson"))
                .body("data.email[0]", is(usersEmail));
    }

    @DisplayName("получить список всех пользователей (пример с урока)")
    @Test
    void checkEmailUsingGroovy() {
        given()
                .log().uri()
                .baseUri(BASE_URL)
                .when()
                .param("page", 1)
                .get(GET_USERS_LIST_ENDPOINT)
                .then()
                .log().body()
                .body("data.findAll{it.email =~/.*?@reqres.in/}.email.flatten()",
                        hasItem("eve.holt@reqres.in"));
    }

    @DisplayName("получить список пользователей методом POST (негативный сценарий)")
    @Test
    void getListUsersByWrongMethodNegativeTest() {
        given()
                .log().uri()
                .baseUri(BASE_URL)
                .when()
                .param("page", 0)
                .post(GET_USERS_LIST_ENDPOINT)
                .then()
                .log().status()
                .log().body()
                .statusCode(415);
    }
}
