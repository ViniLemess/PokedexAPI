package com.fundatec.vinilemess.pokedex.converter;

import com.fundatec.vinilemess.pokedex.domain.Move;
import com.fundatec.vinilemess.pokedex.domain.Pokemon;
import com.fundatec.vinilemess.pokedex.domain.Type;
import com.fundatec.vinilemess.pokedex.service.dto.MoveDTO;
import com.fundatec.vinilemess.pokedex.service.dto.PokemonDTO;
import com.fundatec.vinilemess.pokedex.service.dto.TypeDTO;
import com.fundatec.vinilemess.pokedex.service.external.PokemonResponse;

public class PokemonConverter {

    private PokemonConverter(){}

    public static PokemonDTO responseToDto(PokemonResponse response) {
        return new PokemonDTO(
                response.getId(),
                response.getName(),
                response.getWeight(),
                response.getHeight(),
                response.getMoves()
                        .stream()
                        .map(move -> new MoveDTO(move.getMove().getName()))
                        .toList(),
                response.getTypes()
                        .stream()
                        .map(type -> new TypeDTO(type.getSlot(), type.getType().getName()))
                        .toList()
        );
    }

    public static PokemonDTO entityToDto(Pokemon pokemon) {
        return new PokemonDTO(
                pokemon.getPokedexId(),
                pokemon.getName(),
                pokemon.getWeight(),
                pokemon.getHeight(),
                pokemon.getMoves()
                        .stream()
                        .map(move -> new MoveDTO(move.getName()))
                        .toList(),
                pokemon.getTypes()
                        .stream()
                        .map(type -> new TypeDTO(type.getSlot(), type.getName()))
                        .toList()
        );
    }

    public static Pokemon dtoToEntity(PokemonDTO pokemonDTO) {
        return new Pokemon(
                null,
                pokemonDTO.getPokedexId(),
                pokemonDTO.getName(),
                pokemonDTO.getWeight(),
                pokemonDTO.getHeight(),
                pokemonDTO.getMoves()
                        .stream()
                        .map(moveDTO -> new Move(moveDTO.getName()))
                        .toList(),
                pokemonDTO.getTypes()
                        .stream()
                        .map(typeDTO -> new Type(typeDTO.getSlot(), typeDTO.getName()))
                        .toList()
        );
    }
}
