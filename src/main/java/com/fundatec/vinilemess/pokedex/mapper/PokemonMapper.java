package com.fundatec.vinilemess.pokedex.mapper;

import com.fundatec.vinilemess.pokedex.dto.request.PokemonRequest;
import com.fundatec.vinilemess.pokedex.dto.response.PokemonResponse;
import com.fundatec.vinilemess.pokedex.entity.Pokemon;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PokemonMapper {

    public static PokemonResponse entityToResponse(Pokemon pokemon) {
        return new PokemonResponse(
                pokemon.getPokedexId(),
                pokemon.getName(),
                pokemon.getWeight(),
                pokemon.getHeight(),
                MoveMapper.entitiesToResponses(pokemon.getMoves()),
                TypeMapper.entitiesToResponses(pokemon.getTypes()));
    }

    public static Pokemon requestToEntity(PokemonRequest pokemonRequest) {
        return Pokemon.builder()
                .pokedexId(pokemonRequest.getPokedexId())
                .name(pokemonRequest.getName())
                .weight(pokemonRequest.getWeight())
                .height(pokemonRequest.getHeight())
                .moves(MoveMapper.requestsToEntities(pokemonRequest.getMoves()))
                .types(TypeMapper.requestsToEntities(pokemonRequest.getTypes()))
                .build();
    }
}
