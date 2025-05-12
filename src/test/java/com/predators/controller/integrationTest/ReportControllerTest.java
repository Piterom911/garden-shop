package com.predators.controller.integrationTest;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import static io.restassured.RestAssured.given;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@DirtiesContext
class ReportControllerTest {

    @LocalServerPort
    private int port;

    private String token;

    @BeforeEach
    public void init() {
        RestAssured.baseURI = "http://localhost";
        token = given()
                .contentType("application/json")
                .body("{\"email\":\"test\", \"password\":\"12345\"}")
                .when()
                .post("v1/users/login")
                .then()
                .statusCode(200)
                .extract()
                .path("token");
    }

    @Test
    void testGetTopProductStatusCompleted() {
        given()
                .port(port)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/v1/report/top-product?status=COMPLETED")
                .then()
                .statusCode(200);
    }

    @Test
    void testGetTopProductStatusCancelled() {
        given()
                .port(port)
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/v1/report/top-product?status=CANCELLED")
                .then()
                .statusCode(200);
    }



}