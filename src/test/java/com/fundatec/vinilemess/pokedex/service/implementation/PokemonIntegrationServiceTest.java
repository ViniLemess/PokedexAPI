package com.fundatec.vinilemess.pokedex.service.implementation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.web.client.RestTemplateBuilder;

import static org.junit.jupiter.api.Assertions.*;

class PokemonIntegrationServiceTest {

    @Test
    void mustValidatePokemonExistenceByNameSuccessfully() {
        PokemonIntegrationService service = new PokemonIntegrationService(new RestTemplateBuilder());
        assertTrue(service.validatePokemonExistenceByName("pikachu"));
    }
}