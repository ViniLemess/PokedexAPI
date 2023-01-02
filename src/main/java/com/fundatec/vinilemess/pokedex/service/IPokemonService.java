package com.fundatec.vinilemess.pokedex.service;

import com.fundatec.vinilemess.pokedex.dto.response.PokemonResponse;
import com.fundatec.vinilemess.pokedex.entity.Pokemon;
import com.fundatec.vinilemess.pokedex.dto.request.PokemonRequest;

import java.util.List;

public interface IPokemonService {

    PokemonResponse getPokemonById(Integer id);
    PokemonResponse getPokemonByName(String name);

    List<PokemonResponse> getPokemonsByWeight(Integer weight);

    Pokemon registerPokemon(PokemonRequest pokemon);

    void deletePokemon(String name);

    void updatePokemon(PokemonRequest pokemon);
}
