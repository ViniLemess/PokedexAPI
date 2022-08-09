package com.fundatec.vinilemess.pokedex.rest.controller.impl;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

class PokemonControllerTest {

    @Test
    void givenValidId_mustGetPokemon_withStatus200() {
        get("http://localhost:8081/api/v1/pokemon/132")
                .then()
                .statusCode(200)
                .body(equalTo("{\"pokedexId\":132,\"name\":\"ditto\",\"weight\":3,\"height\":40,\"moves\":[{\"name\":\"transform\"}],\"types\":[{\"slot\":1,\"name\":\"normal\"}]}"));
    }

    @Test
    void givenNonexistentId_mustGetPokemon_withStatus404() {
        get("http://localhost:8081/api/v1/pokemon/100000")
                .then()
                .statusCode(404);
    }

    @Test
    void givenValidName_mustGetPokemon_withStatus200() {
        get("http://localhost:8081/api/v1/pokemon/name/ditto")
                .then()
                .statusCode(200)
                .body(equalTo("{\"pokedexId\":132,\"name\":\"ditto\",\"weight\":3,\"height\":40,\"moves\":[{\"name\":\"transform\"}],\"types\":[{\"slot\":1,\"name\":\"normal\"}]}"));
    }

    @Test
    void givenNonexistentName_mustGetPokemon_withStatus404() {
        get("http://localhost:8081/api/v1/pokemon/name/nonexistentmon")
                .then()
                .statusCode(404);
    }

    @Test
    void givenValidBody_mustPostPokemon_withStatus201() {
        String requestBody = "{\n" +
                "  \"height\": 1,\n" +
                "  \"pokedexId\": 1051,\n" +
                "  \"name\": \"Salenmon\",\n" +
                "  \"weight\": 5,\n" +
                "  \"moves\": [\n" +
                "      {\n" +
                "          \"name\": \"Bite\"\n" +
                "      },\n" +
                "      {\n" +
                "          \"name\": \"Pursuit\"\n" +
                "      }\n" +
                "  ],\n" +
                "  \"types\": [\n" +
                "      {\n" +
                "          \"slot\": 1,\n" +
                "          \"name\": \"dark\"\n" +
                "      }\n" +
                "  ]\n" +
                "}";

        given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .post("http://localhost:8081/api/v1/pokemon")
                .then()
                .statusCode(201);
    }

    @Test
    void givenValidBody_mustPutPokemon_withStatus200() {
        String requestBody = "{\n" +
                "    \"pokedexId\": 1050,\n" +
                "    \"name\": \"fecha\",\n" +
                "    \"weight\": 20,\n" +
                "    \"height\": 30,\n" +
                "    \"moves\": [\n" +
                "        {\n" +
                "            \"name\": \"Psybeam\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"Psychic\"\n" +
                "        },\n" +
                "        {\n" +
                "            \"name\": \"Confusion\"\n" +
                "        }\n" +
                "    ],\n" +
                "    \"types\": [\n" +
                "        {\n" +
                "            \"slot\": 1,\n" +
                "            \"name\": \"psychic\"\n" +
                "        }\n" +
                "    ]\n" +
                "}";

        given()
                .header("Content-type", "application/json")
                .and()
                .body(requestBody)
                .when()
                .put("http://localhost:8081/api/v1/pokemon")
                .then()
                .statusCode(200);
    }

    @Test
    void givenNonexistentName_mustDeletePokemon_withStatus404() {
        delete("http://localhost:8081/api/v1/pokemon/nonexistentmon")
                .then()
                .statusCode(404);
    }

    @Test
    void givenCanonicalPokemonName_mustDeletePokemon_withStatus403() {
        delete("http://localhost:8081/api/v1/pokemon/pikachu")
                .then()
                .statusCode(403);
    }

    @AfterAll
    static void deletePokemon() {
        delete("http://localhost:8081/api/v1/pokemon/Salenmon")
                .then()
                .statusCode(200);
    }
}