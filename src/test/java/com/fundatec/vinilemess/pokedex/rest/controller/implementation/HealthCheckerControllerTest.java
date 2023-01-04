package com.fundatec.vinilemess.pokedex.rest.controller.implementation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnJre;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HealthCheckerControllerTest {

    @Test
    void whenRequestGet_thenOk() {
        get("http://localhost:8081/api/v2/health")
                .then()
                .statusCode(200)
                .assertThat()
                .body(equalToObject("The application is running fine!"));
    }
}