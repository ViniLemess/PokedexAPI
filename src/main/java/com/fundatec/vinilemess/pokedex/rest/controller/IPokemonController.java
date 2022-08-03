package com.fundatec.vinilemess.pokedex.rest.controller;

import com.fundatec.vinilemess.pokedex.domain.entity.Pokemon;
import org.springframework.http.ResponseEntity;

public interface IPokemonController {

    ResponseEntity<Pokemon> getPokemonById(Integer id);
    ResponseEntity<Pokemon> getPokemonByName(String name);
    ResponseEntity<Iterable<Pokemon>> getHeavyPokemons();
    void registerPokemon(Pokemon pokemon);
    void deletePokemon(String name);
    void updatePokemon(Pokemon pokemon);
}
