package com.predators.controller.integrationTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import static io.restassured.RestAssured.given;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest {

    @LocalServerPort
    private int port;

    @BeforeEach
    public void init() {
        RestAssured.baseURI = "http://localhost";
    }

    @Test
    void testGetAll() {
        given()
                .port(port)
                .when()
                .get("v1/products")
                .then()
                .statusCode(200);
    }

    @Test
    void testGetById(){
        given()
                .port(port)
                .when()
                .get("v1/products/1")
                .then()
                .statusCode(200);
    }

    @Test
    void testCreate() {
        String user = "{\n" +
                "  \"name\": \"one\",\n" +
                "  \"description\": \"one description\",\n" +
                "  \"price\": \"20.89\",\n" +
                "  \"categoryId\": \"1\",\n" +
                "  \"image\": \"http/\"\n" +
                "}";
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .post("v1/products")
                .then()
                .statusCode(201);
    }

    @Test
    void testUpdate() {
        String user = "{\n" +
                "  \"name\": \"one\",\n" +
                "  \"description\": \"one description\",\n" +
                "  \"price\": \"10.12\",\n" +
                "  \"categoryId\": \"1\",\n" +
                "  \"image\": \"http/\"\n" +
                "}";
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put("v1/products/2")
                .then()
                .statusCode(201);
    }


    @Test
    void testDelete() {
        given()
                .port(port)
                .when()
                .delete("v1/products/1")
                .then()
                .statusCode(200);
    }

}