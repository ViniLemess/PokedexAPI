package com.fundatec.vinilemess.pokedex.service;

import com.fundatec.vinilemess.pokedex.domain.entity.Pokemon;

public interface IPokemonService {

    Pokemon getPokemonById(Integer id);
    Pokemon getPokemonByName(String name);

    Pokemon registerPokemon(Pokemon pokemon);

    void deletePokemon(String name);

    void updatePokemon(Pokemon pokemon);
}
