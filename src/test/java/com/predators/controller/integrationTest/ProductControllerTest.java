package com.predators.controller.integrationTest;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
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
        String user = """
                {
                  "name": "one",
                  "description": "one description",
                  "price": "20.89",
                  "categoryId": "1",
                  "image": "http/"
                }""";
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
        String user = """
                {
                  "name": "one",
                  "description": "one description",
                  "price": "10.12",
                  "categoryId": "1",
                  "image": "http/"
                }""";
        given()
                .port(port)
                .contentType(ContentType.JSON)
                .body(user)
                .when()
                .put("v1/products/1")
                .then()
                .statusCode(201);
    }


    @Test
    @Transactional
    void testDelete() {
        given()
                .port(port)
                .when()
                .delete("v1/products/2")
                .then()
                .statusCode(200);
    }

}