package gmail.salokin1991.tests;


import io.qameta.allure.restassured.AllureRestAssured;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static gmail.salokin1991.filters.CustomLogFilter.customLogFilter;
import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static org.hamcrest.Matchers.*;

@Disabled
public class BookStoreTests {

    @Test
    void noLogsTest() {

        given()
                .get("https://demoqa.com/BookStore/v1/Books")
                .then()
                .body("books", hasSize(greaterThan(0)));
    }

    @Test
    void withAllLogsTest() {

        given()
                .log().all()
                .get("https://demoqa.com/BookStore/v1/Books")
                .then()
                .log().all()
                .body("books", hasSize(greaterThan(0)));
    }

    @Test
    void withSomeLogsTest() {

        given()
                .log().uri()
                .log().body()
                .get("https://demoqa.com/BookStore/v1/Books")
                .then()
                .log().body()
                .body("books", hasSize(greaterThan(0)));
    }


    @Test
    void authorizeTest() {

        String data = "{" +
                "  \"userName\": \"alex\"," +
                "  \"password\": \"asdsad#frew_DFS2\"" +
                "}";

//        Map<String, String> data = new HashMap<>();
//        data.put("userName", "alex");
//        data.put("password", "asdsad#frew_DFS2");

        given()
                .contentType("application/json")
                .accept("application/json")
                .body(data.toString())
                .when()
                .log().uri()
                .log().body()
                .post("https://demoqa.com/Account/v1/GenerateToken")
                .then()
                .log().body()
                .body("status", is("Success"))
                .body("result", is("User authorized successfully."));
    }

    @Test
    void authorizeWithListenerTest() {

        String data = "{" +
                "  \"userName\": \"alex\"," +
                "  \"password\": \"asdsad#frew_DFS2\"" +
                "}";

        given()
                .filter(new AllureRestAssured())
                .contentType("application/json")
                .accept("application/json")
                .body(data.toString())
                .when()
                .log().uri()
                .log().body()
                .post("https://demoqa.com/Account/v1/GenerateToken")
                .then()
                .log().body()
                .body("status", is("Success"))
                .body("result", is("User authorized successfully."));
    }

    @Test
    void authorizeWithTemplatesTest() {

        String data = "{" +
                "  \"userName\": \"alex\"," +
                "  \"password\": \"asdsad#frew_DFS2\"" +
                "}";

        step("Generate token", () ->
                given()
                        .filter(customLogFilter().withCustomTemplates())
                        .contentType("application/json")
                        .accept("application/json")
                        .body(data.toString())
                        .when()
                        .log().uri()
                        .log().body()
                        .post("https://demoqa.com/Account/v1/GenerateToken")
                        .then()
                        .log().body()
                        .body("status", is("Success"))
                        .body("result", is("User authorized successfully.")));

        step("Any UI action");
    }

    @Test
    void authorizeWithShemeTest() {

        String data = "{" +
                "  \"userName\": \"alex\"," +
                "  \"password\": \"asdsad#frew_DFS2\"" +
                "}";

        given()
                .filter(new AllureRestAssured())
                .contentType("application/json")
                .accept("application/json")
                .body(data.toString())
                .when()
                .log().uri()
                .log().body()
                .post("https://demoqa.com/Account/v1/GenerateToken")
                .then()
                .log().body()
                .body(matchesJsonSchemaInClasspath("sсhemas/GenerateTokenSсheme.json"))
                .body("status", is("Success"))
                .body("result", is("User authorized successfully."));
    }
}
