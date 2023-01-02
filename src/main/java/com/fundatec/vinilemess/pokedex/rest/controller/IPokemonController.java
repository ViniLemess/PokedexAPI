package com.fundatec.vinilemess.pokedex.rest.controller;

import com.fundatec.vinilemess.pokedex.dto.request.PokemonRequest;
import com.fundatec.vinilemess.pokedex.dto.response.PokemonResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import org.springframework.http.ResponseEntity;

import java.util.List;

public interface IPokemonController {

    @Operation(summary = "GET pokemon by id", description = "Return pokemon by the given id")
    ResponseEntity<PokemonResponse> getPokemonById(
            @Parameter(schema = @Schema(implementation = Integer.class),
                      description = "Id of the desired pokemon",
                      required = true,
                      example = "1") Integer id);

    @Operation(summary = "GET pokemon by name", description = "Return pokemon by the given name")
    ResponseEntity<PokemonResponse> getPokemonByName(
            @Parameter(schema = @Schema(implementation = String.class),
                    description = "Name of the desired pokemon",
                    required = true,
                    example = "pikachu") String name);

    @Operation(summary = "GET list of pokemon by weight",
            description = "Return pokemon with weight equal or higher than the given")
    ResponseEntity<List<PokemonResponse>> getPokemonsByWeight(
            @Parameter(schema = @Schema(implementation = Integer.class),
                    description = "Value of the desired weight cut.",
                    required = true,
                    example = "50") Integer weight);

    @Operation(summary = "POST pokemon",
            description = "Register new pokemon")
    void registerPokemon(
            PokemonRequest pokemon);

    @Operation(summary = "DELETE pokemon by name",
            description = "Delete pokemon if exists in the internal db by the given name")
    void deletePokemon(String name);

    @Operation(summary = "PUT pokemon",
            description = "Update pokemon unless its an external")
    void updatePokemon(PokemonRequest pokemon);
}
