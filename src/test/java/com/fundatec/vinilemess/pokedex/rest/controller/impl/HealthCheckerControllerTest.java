package com.fundatec.vinilemess.pokedex.rest.controller.impl;

import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

class HealthCheckerControllerTest {

    @Test
    void whenRequestGet_thenOk() {
        get("http://localhost:8081/api/v1/health")
                .then()
                .statusCode(200)
                .assertThat()
                .body(equalToObject("The application is running fine!"));
    }
}