package com.fundatec.vinilemess.pokedex.service.implementation;

import com.fundatec.vinilemess.pokedex.exception.PokemonNotFoundException;
import com.fundatec.vinilemess.pokedex.service.external.PokemonResponse;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class PokemonIntegrationServiceTest {

    @Autowired
    private PokemonIntegrationService integrationService;

    @Test
    void mustGetPokemonResponseByIdSuccessfully() {
        var pokemon = integrationService.getPokemonResponseById(1);
        assertInstanceOf(PokemonResponse.class, pokemon);
        assertEquals(1, pokemon.getId());
    }

    @Test
    void mustValidateGetPokemonResponseByIdWhenNotFound() {
        Exception exception = assertThrows(PokemonNotFoundException.class, () -> integrationService.getPokemonResponseById(906));
        assertEquals("Could not found : Pokemon", exception.getMessage());
    }

    @Test
    void mustGetPokemonResponseByNameSuccessfully() {
        var pokemon = integrationService.getPokemonResponseByName("pikachu");
        assertInstanceOf(PokemonResponse.class, pokemon);
        assertEquals("pikachu", pokemon.getName());
    }

    @Test
    void mustvalidateGetPokemonResponseByNameWhenNotFound() {
        Exception exception = assertThrows(PokemonNotFoundException.class, () -> integrationService.getPokemonResponseByName("Testemon"));
        assertEquals("Could not found : Pokemon", exception.getMessage());
    }

    @Test
    void mustReturnTrueWhenValidatePokemonByExistentName() {
        assertTrue(integrationService.validatePokemonExistenceByName("pikachu"));
    }

    @Test
    void mustReturnFalseWhenValidatePokemonByNonexistentName() {
        assertFalse(integrationService.validatePokemonExistenceByName("Testemon"));
    }
}