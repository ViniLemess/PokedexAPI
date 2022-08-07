package com.fundatec.vinilemess.pokedex.service;

import com.fundatec.vinilemess.pokedex.domain.Pokemon;
import com.fundatec.vinilemess.pokedex.service.dto.PokemonDTO;

import java.util.List;

public interface IPokemonService {

    PokemonDTO getPokemonById(Integer id);
    PokemonDTO getPokemonByName(String name);

    List<PokemonDTO> getPokemonsByWeight(Integer weight);

    Pokemon registerPokemon(PokemonDTO pokemon);

    void deletePokemon(String name);

    void updatePokemon(PokemonDTO pokemon);
}
