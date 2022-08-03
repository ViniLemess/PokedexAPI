package com.fundatec.vinilemess.pokedex.converter;

import com.fundatec.vinilemess.pokedex.domain.entity.Move;
import com.fundatec.vinilemess.pokedex.domain.entity.Pokemon;
import com.fundatec.vinilemess.pokedex.domain.entity.Type;
import com.fundatec.vinilemess.pokedex.service.external.PokemonResponse;

public class PokemonConverter {
    public static Pokemon toEntity(PokemonResponse response) {
        return new Pokemon(
                response.getId(),
                response.getName(),
                response.getHeight(),
                response.getWeight(),
                response.getMoves()
                        .stream()
                        .map(move -> new Move(move.getMove().getName()))
                        .toList(),
                response.getTypes()
                        .stream()
                        .map(type -> new Type(type.getSlot(), type.getType().getName()))
                        .toList()
        );
    }
}
