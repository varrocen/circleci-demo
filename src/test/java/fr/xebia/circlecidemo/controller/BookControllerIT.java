package fr.xebia.circlecidemo.controller;

import fr.xebia.circlecidemo.domain.Book;
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
public class BookControllerIT {

    @LocalServerPort
    private int port;

    @Test
    public void should_get_all_books() {
        given()
                .port(port)
                .when().get("/books")
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("size()", equalTo(2))
                .body("[0].title", equalTo("La Huitième Couleur"))
                .body("[0].publicationDate", equalTo("1983"))
                .body("[0].author.name", equalTo("Terry Pratchett"))
                .body("[1].title", equalTo("American Gods"))
                .body("[1].publicationDate", equalTo("2001"))
                .body("[1].author.name", equalTo("Neil Gaiman"));
    }

    @Test
    public void should_get_book() {
        given()
                .port(port)
                .when().get("/books/1")
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("title", equalTo("La Huitième Couleur"))
                .body("publicationDate", equalTo("1983"))
                .body("author.name", equalTo("Terry Pratchett"));
    }

    @Test
    public void should_get_all_books_from_an_author() {
        given()
                .port(port)
                .when().get("/authors/1/books")
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("size()", equalTo(1))
                .body("[0].title", equalTo("La Huitième Couleur"))
                .body("[0].publicationDate", equalTo("1983"))
                .body("[0].author.name", equalTo("Terry Pratchett"));
    }

    @Test
    public void should_put_new_book() {
        given()
                .port(port)
                .body(toJson(new Book("Le Huitième Sortilège", "1986")))
                .contentType(JSON)
                .when().put("/authors/1/books/3")
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("title", equalTo("Le Huitième Sortilège"))
                .body("publicationDate", equalTo("1986"))
                .body("author.name", equalTo("Terry Pratchett"));
    }

    @Test
    public void should_put_existing_book() {
        given()
                .port(port)
                .body(toJson(new Book("Le Huitième Sortilège", "1986")))
                .contentType(JSON)
                .when().put("/authors/2/books/2")
                .then()
                .log().ifValidationFails()
                .assertThat()
                .body("title", equalTo("Le Huitième Sortilège"))
                .body("publicationDate", equalTo("1986"))
                .body("author.name", equalTo("Neil Gaiman"));
    }

    @Test
    public void should_delete_book() {
        given()
                .port(port)
                .when().delete("/books/2")
                .then()
                .log().ifValidationFails()
                .assertThat()
                .contentType(isEmptyOrNullString());
    }
}
