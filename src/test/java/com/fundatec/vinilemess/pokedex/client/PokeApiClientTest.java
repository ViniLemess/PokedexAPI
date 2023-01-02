package com.fundatec.vinilemess.pokedex.client;

import com.fundatec.vinilemess.pokedex.client.implementation.PokeApiClient;
import com.fundatec.vinilemess.pokedex.dto.response.PokemonResponse;
import com.fundatec.vinilemess.pokedex.exception.PokemonNotFoundException;
import feign.FeignException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class PokeApiClientTest {

    @Autowired
    private PokeApiClient pokeApiClient;

    @Test
    void mustGetPokemonResponseByIdSuccessfully() {
        var pokemon = pokeApiClient.findPokemonResponseById(1);
        assertInstanceOf(PokemonResponse.class, pokemon);
        assertEquals(1, pokemon.getId());
    }

    @Test
    void mustValidateGetPokemonResponseByIdWhenNotFound() {
        Exception exception = assertThrows(FeignException.NotFound.class, () -> pokeApiClient.findPokemonResponseById(906));
    }

    @Test
    void mustGetPokemonResponseByNameSuccessfully() {
        var pokemon = pokeApiClient.findPokemonResponseByName("pikachu");
        assertInstanceOf(PokemonResponse.class, pokemon);
        assertEquals("pikachu", pokemon.getName());
    }

    @Test
    void mustvalidateGetPokemonResponseByNameWhenNotFound() {
        Exception exception = assertThrows(FeignException.NotFound.class, () -> pokeApiClient.findPokemonResponseByName("Testemon"));
    }

    @Test
    void mustReturnTrueWhenValidatePokemonByExistentName() {
        assertTrue(pokeApiClient.validatePokemonExistenceByName("pikachu"));
    }

    @Test
    void mustReturnFalseWhenValidatePokemonByNonexistentName() {
        assertFalse(pokeApiClient.validatePokemonExistenceByName("Testemon"));
    }
}