package com.fundatec.vinilemess.pokedex.client;

import com.fundatec.vinilemess.pokedex.dto.response.PokemonResponse;

public interface IPokemonApiClient {
    PokemonResponse findPokemonResponseById(Integer id);
    PokemonResponse findPokemonResponseByName(String name);

    boolean validatePokemonExistenceByName(String name);
}
