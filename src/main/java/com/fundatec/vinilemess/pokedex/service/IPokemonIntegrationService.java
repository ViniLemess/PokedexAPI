package com.fundatec.vinilemess.pokedex.service;

import com.fundatec.vinilemess.pokedex.service.external.PokemonResponse;

public interface IPokemonIntegrationService {
    PokemonResponse getPokemonResponseById(Long id);
    PokemonResponse getPokemonResponseByName(String name);

    boolean validatePokemonExistenceByName(String name);
}
