package com.fundatec.vinilemess.pokedex.converter;

import com.fundatec.vinilemess.pokedex.entity.Move;
import com.fundatec.vinilemess.pokedex.entity.Pokemon;
import com.fundatec.vinilemess.pokedex.entity.Type;
import com.fundatec.vinilemess.pokedex.dto.request.MoveRequest;
import com.fundatec.vinilemess.pokedex.dto.request.PokemonRequest;
import com.fundatec.vinilemess.pokedex.dto.request.TypeRequest;
import com.fundatec.vinilemess.pokedex.dto.response.PokemonResponse;

public class PokemonConverter {

    private PokemonConverter(){}

    public static PokemonRequest responseToDto(PokemonResponse response) {
        return new PokemonRequest(
                response.getId(),
                response.getName(),
                response.getWeight(),
                response.getHeight(),
                response.getMoves()
                        .stream()
                        .map(move -> new MoveRequest(move.getMove().getName()))
                        .toList(),
                response.getTypes()
                        .stream()
                        .map(type -> new TypeRequest(type.getSlot(), type.getType().getName()))
                        .toList()
        );
    }

    public static PokemonRequest entityToDto(Pokemon pokemon) {
        return new PokemonRequest(
                pokemon.getPokedexId(),
                pokemon.getName(),
                pokemon.getWeight(),
                pokemon.getHeight(),
                pokemon.getMoves()
                        .stream()
                        .map(move -> new MoveRequest(move.getName()))
                        .toList(),
                pokemon.getTypes()
                        .stream()
                        .map(type -> new TypeRequest(type.getSlot(), type.getName()))
                        .toList()
        );
    }

    public static Pokemon dtoToEntity(PokemonRequest pokemonRequest) {
        return new Pokemon(
                null,
                pokemonRequest.getPokedexId(),
                pokemonRequest.getName(),
                pokemonRequest.getWeight(),
                pokemonRequest.getHeight(),
                pokemonRequest.getMoves()
                        .stream()
                        .map(moveRequest -> new Move(moveRequest.getName()))
                        .toList(),
                pokemonRequest.getTypes()
                        .stream()
                        .map(typeRequest -> new Type(typeRequest.getSlot(), typeRequest.getName()))
                        .toList()
        );
    }
}
