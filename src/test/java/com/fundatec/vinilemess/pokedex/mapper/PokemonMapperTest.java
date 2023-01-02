package com.fundatec.vinilemess.pokedex.mapper;

import com.fundatec.vinilemess.pokedex.dto.request.MoveRequest;
import com.fundatec.vinilemess.pokedex.dto.request.PokemonRequest;
import com.fundatec.vinilemess.pokedex.dto.request.TypeRequest;
import com.fundatec.vinilemess.pokedex.dto.response.PokemonResponse;
import com.fundatec.vinilemess.pokedex.entity.Move;
import com.fundatec.vinilemess.pokedex.entity.Pokemon;
import com.fundatec.vinilemess.pokedex.entity.Type;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;

class PokemonMapperTest {

    @Test
    void mustMapFromEntityToResponseSuccessfully() {
        var pokemon = Pokemon.builder()
                .id(null)
                .pokedexId(1)
                .name("Testemon")
                .height(4)
                .weight(40)
                .moves(List.of(new Move("TestAtack")))
                .types(List.of(new Type(1, "Flying")))
                .build();
        assertInstanceOf(PokemonResponse.class, PokemonMapper.entityToResponse(pokemon));
    }

    @Test
    void mustMapFromRequestToEntitySuccessfully() {
        var pokemonDTO = new PokemonRequest(
                1,
                "Testemon",
                4,
                40,
                List.of(new MoveRequest("TestAtack")),
                List.of(new TypeRequest(1, "Flying"))
        );
        assertInstanceOf(Pokemon.class, PokemonMapper.requestToEntity(pokemonDTO));
    }
}