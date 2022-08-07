package com.fundatec.vinilemess.pokedex.rest.controller;

import com.fundatec.vinilemess.pokedex.service.dto.PokemonDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPokemonController {

    ResponseEntity<PokemonDTO> getPokemonById(Integer id);
    ResponseEntity<PokemonDTO> getPokemonByName(String name);
    ResponseEntity<List<PokemonDTO>> getPokemonsByWeight(Integer weight);
    void registerPokemon(PokemonDTO pokemon);
    void deletePokemon(String name);
    void updatePokemon(PokemonDTO pokemon);
}
