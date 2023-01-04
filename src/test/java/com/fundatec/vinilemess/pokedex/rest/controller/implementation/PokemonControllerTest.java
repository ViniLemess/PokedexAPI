package com.fundatec.vinilemess.pokedex.rest.controller.implementation;

import com.fundatec.vinilemess.pokedex.dto.request.PokemonRequest;
import com.fundatec.vinilemess.pokedex.exception.ExternalAlterationException;
import com.fundatec.vinilemess.pokedex.exception.PokemonNotFoundException;
import com.fundatec.vinilemess.pokedex.service.implementation.PokemonService;
import com.fundatec.vinilemess.pokedex.stubs.PokemonStub;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PokemonControllerTest {

    @LocalServerPort
    private int port;
    private static final String HTTP_CONTEXT_PATH = "/api/v2";
    @MockBean
    PokemonService pokemonService;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    @Nested
    @DisplayName("When call method [getPokemonById()]...")
    class GetPokemonByIdTest {

        @Test
        @DisplayName("Must return ditto response with status code 200")
        void getPokemonWithStatus200() {
            Mockito.when(pokemonService.getPokemonById(132)).thenReturn(PokemonStub.createCanonicalResponseStub());
            get(HTTP_CONTEXT_PATH + "/pokemon/{id}", 132)
                    .then()
                    .statusCode(200)
                    .body(equalTo("{\"id\":132,\"name\":\"ditto\",\"height\":3,\"weight\":40,\"moves\":[{\"move\":{\"name\":\"transform\"}}],\"types\":[{\"slot\":1,\"type\":{\"name\":\"normal\"}}]}"));
        }

        @Test
        @DisplayName("Must return not found message response with status code 404")
        void getPokemonWithStatus404() {
            Mockito.when(pokemonService.getPokemonById(100000)).thenThrow(new PokemonNotFoundException("pokemon 100000"));
            get(HTTP_CONTEXT_PATH + "/pokemon/{id}", 100000)
                    .then()
                    .statusCode(404)
                    .body(containsString("Could not found : pokemon 100000"));
        }
    }

    @Nested
    @DisplayName("When call method [getPokemonByName()]...")
    class GetPokemonByNameTest {

        @Test
        @DisplayName("Must return ditto response with status code 200")
        void getPokemonWithStatus200() {
            Mockito.when(pokemonService.getPokemonByName("ditto")).thenReturn(PokemonStub.createCanonicalResponseStub());
            get(HTTP_CONTEXT_PATH + "/pokemon/name/{name}", "ditto")
                    .then()
                    .statusCode(200)
                    .body(equalTo("{\"id\":132,\"name\":\"ditto\",\"height\":3,\"weight\":40,\"moves\":[{\"move\":{\"name\":\"transform\"}}],\"types\":[{\"slot\":1,\"type\":{\"name\":\"normal\"}}]}"));
        }

        @Test
        @DisplayName("Must return not found message response with status code 404")
        void getPokemonWithStatus404() {
            Mockito.when(pokemonService.getPokemonByName(Mockito.anyString())).thenThrow(new PokemonNotFoundException("pokemon nonexistentmon"));
            get(HTTP_CONTEXT_PATH + "/pokemon/name/{name}", "nonexistentmon")
                    .then()
                    .statusCode(404)
                    .body(containsString("Could not found : pokemon nonexistentmon"));
        }
    }

    @Nested
    @DisplayName("When call method [registerPokemon()]...")
    class RegisterPokemonTest {

        @Test
        @DisplayName("Must return status code 201")
        void postPokemonWithStatus201() {
            Mockito.when(pokemonService.registerPokemon(Mockito.any(PokemonRequest.class))).thenReturn(PokemonStub.createFakemonStub());

            String requestBody = """
                {
                  "height": 1,
                  "pokedexId": 1051,
                  "name": "Salenmon",
                  "weight": 5,
                  "moves": [
                      {
                          "name": "Bite"
                      },
                      {
                          "name": "Pursuit"
                      }
                  ],
                  "types": [
                      {
                          "slot": 1,
                          "name": "dark"
                      }
                  ]
                }""";

            given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(requestBody)
                    .when()
                    .post(HTTP_CONTEXT_PATH + "/pokemon")
                    .then()
                    .statusCode(201);
        }
    }

    @Nested
    @DisplayName("When call method (updatePokemon()]...")
    class UpdatePokemonTest {

        @Test
        @DisplayName("With valid body must return status code 200")
        void putPokemonTestWithStatus200() {
            Mockito.doNothing().when(pokemonService).updatePokemon(Mockito.any(PokemonRequest.class));

            String requestBody = """
                {
                  "height": 20,
                  "pokedexId": 1050,
                  "name": "fecha",
                  "weight": 50,
                  "moves": [
                      {
                          "name": "Psybeam"
                      },
                      {
                          "name": "Psychic"
                      }
                  ],
                  "types": [
                      {
                          "slot": 1,
                          "name": "psychic"
                      }
                  ]
                }""";

            given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(requestBody)
                    .when()
                    .put(HTTP_CONTEXT_PATH + "/pokemon")
                    .then()
                    .statusCode(200);
        }

        @Test
        @DisplayName("With valid body must return status code 403")
        void putPokemonTestWithStatus403() {
            Mockito.doThrow(ExternalAlterationException.class).when(pokemonService).updatePokemon(Mockito.any(PokemonRequest.class));

            String requestBody = """
                {
                  "height": 3,
                  "pokedexId": 132,
                  "name": "ditto",
                  "weight": 40,
                  "moves": [
                      {
                          "name": "transform"
                      }
                  ],
                  "types": [
                      {
                          "slot": 1,
                          "name": "normal"
                      }
                  ]
                }""";

            given()
                    .header("Content-type", "application/json")
                    .and()
                    .body(requestBody)
                    .when()
                    .put(HTTP_CONTEXT_PATH + "/pokemon")
                    .then()
                    .statusCode(403);
        }
    }

    @Nested
    @DisplayName("When call method [deletePokemon()]...")
    class DeletePokemonTest {

        @Test
        @DisplayName("With fakemon name must return status code 200")
        void deletePokemonWithStatus200() {
            Mockito.doNothing().when(pokemonService).deletePokemon(Mockito.anyString());
            delete(HTTP_CONTEXT_PATH + "/pokemon/{name}", "testemon")
                    .then()
                    .statusCode(200);
        }

        @Test
        @DisplayName("With nonexistent pokemon name must return status code 404")
        void deletePokemonWithStatus404() {
            Mockito.doThrow(PokemonNotFoundException.class).when(pokemonService).deletePokemon(Mockito.anyString());
            delete(HTTP_CONTEXT_PATH + "/pokemon/{name}", "nonexistentmon")
                    .then()
                    .statusCode(404);
        }

        @Test
        @DisplayName("With canonical pokemon name must return status code 403")
        void deletePokemonWithStatus403() {
            Mockito.doThrow(ExternalAlterationException.class).when(pokemonService).deletePokemon(Mockito.anyString());
            delete(HTTP_CONTEXT_PATH + "/pokemon/{name}", "pikachu")
                    .then()
                    .statusCode(403);
        }
    }
}