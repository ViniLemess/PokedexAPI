package com.fundatec.vinilemess.pokedex.rest.controller.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.JRE;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@DisabledOnJre(JRE.JAVA_17)

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