package fr.xebia.circlecidemo.controller;

import fr.xebia.circlecidemo.domain.Author;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import static groovy.json.JsonOutput.toJson;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.isEmptyOrNullString;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = RANDOM_PORT)
@Sql({"/clear-data.sql", "/insert-data.sql"})
public class AuthorControllerIT {

    @LocalServerPort
    private int port;

    @Test
    public void should_get_all_authors() {
        given()
                .port(port)
                .when().get("/authors")
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("size()", equalTo(2))
                .body("[0].firstName", equalTo("Terry"))
                .body("[0].lastName", equalTo("Pratchett"))
                .body("[1].firstName", equalTo("Neil"))
                .body("[1].lastName", equalTo("Gaiman"));
    }

    @Test
    public void should_post_author() {
        given()
                .port(port)
                .body(toJson(new Author("Howard Phillips", "Lovecraft")))
                .contentType(JSON)
                .when().post("/authors")
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("firstName", equalTo("Howard Phillips"))
                .body("lastName", equalTo("Lovecraft"));
    }

    @Test
    public void should_get_author() {
        given()
                .port(port)
                .when().get("/authors/1")
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("firstName", equalTo("Terry"))
                .body("lastName", equalTo("Pratchett"));
    }

    @Test
    public void should_put_new_author() {
        given()
                .port(port)
                .body(toJson(new Author("Howard Phillips", "Lovecraft")))
                .contentType(JSON)
                .when().put("/authors/3")
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("firstName", equalTo("Howard Phillips"))
                .body("lastName", equalTo("Lovecraft"));
    }

    @Test
    public void should_put_existing_author() {
        given()
                .port(port)
                .body(toJson(new Author("Howard Phillips", "Lovecraft")))
                .contentType(JSON)
                .when().put("/authors/2")
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("id", equalTo(2))
                .body("firstName", equalTo("Howard Phillips"))
                .body("lastName", equalTo("Lovecraft"));
    }

    @Test
    public void should_delete_author() {
        given()
                .port(port)
                .when().delete("/authors/2")
                .then()
                .log().ifValidationFails()
                .assertThat()
                .contentType(isEmptyOrNullString());
    }
}
