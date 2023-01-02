package com.fundatec.vinilemess.pokedex.rest.controller;

import com.fundatec.vinilemess.pokedex.dto.request.PokemonRequest;
import com.fundatec.vinilemess.pokedex.dto.response.PokemonResponse;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPokemonController {

    @ApiOperation(value = "GET pokemon by id", notes = "Return pokemon by the given id")
    ResponseEntity<PokemonResponse> getPokemonById(
            @ApiParam(type = "Integer",
                      value = "Id of the desired pokemon",
                      required = true,
                      example = "1") Integer id);

    @ApiOperation(value = "GET pokemon by name", notes = "Return pokemon by the given name")
    ResponseEntity<PokemonResponse> getPokemonByName(
            @ApiParam(type = "String",
                    value = "Name of the desired pokemon",
                    required = true,
                    example = "pikachu") String name);

    @ApiOperation(value = "GET list of pokemon by weight",
            notes = "Return pokemon with weight equal or higher than the given")
    ResponseEntity<List<PokemonResponse>> getPokemonsByWeight(
            @ApiParam(type = "Integer",
                    value = "Value of the desired weight cut.",
                    required = true,
                    example = "50") Integer weight);

    @ApiOperation(value = "POST pokemon",
            notes = "Register new pokemon")
    void registerPokemon(
            PokemonRequest pokemon);

    @ApiOperation(value = "DELETE pokemon by name",
            notes = "Delete pokemon if exists in the internal db by the given name")
    void deletePokemon(String name);

    @ApiOperation(value = "PUT pokemon",
            notes = "Update pokemon unless its an external")
    void updatePokemon(PokemonRequest pokemon);
}
