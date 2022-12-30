package com.fundatec.vinilemess.pokedex.converter;

import com.fundatec.vinilemess.pokedex.entity.Move;
import com.fundatec.vinilemess.pokedex.entity.Pokemon;
import com.fundatec.vinilemess.pokedex.entity.Type;
import com.fundatec.vinilemess.pokedex.dto.request.MoveRequest;
import com.fundatec.vinilemess.pokedex.dto.request.PokemonRequest;
import com.fundatec.vinilemess.pokedex.dto.request.TypeRequest;
import com.fundatec.vinilemess.pokedex.dto.response.MoveResponse;
import com.fundatec.vinilemess.pokedex.dto.response.PokemonResponse;
import com.fundatec.vinilemess.pokedex.dto.response.TypeResponse;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PokemonConverterTest {

    @Test
    void mustConvertFromResponseToDtoSuccessfully() {
        var pokemonResponse = new PokemonResponse(
                1,
                "Testemon",
                4,
                40,
                Arrays.asList(new MoveResponse("TestAtack")),
                Arrays.asList(new TypeResponse(1, "Flying"))
        );
        assertInstanceOf(PokemonRequest.class, PokemonConverter.responseToDto(pokemonResponse));
    }

    @Test
    void mustConvertFromEntityToDtoSuccessfully() {
        var pokemon = new Pokemon(
                null,
                1,
                "Testemon",
                4,
                40,
                List.of(new Move("TestAtack")),
                List.of(new Type(1, "Flying"))
        );
        assertInstanceOf(PokemonRequest.class, PokemonConverter.entityToDto(pokemon));
    }

    @Test
    void mustConvertFromDtoToEntitySuccessfully() {
        var pokemonDTO = new PokemonRequest(
                1,
                "Testemon",
                4,
                40,
                List.of(new MoveRequest("TestAtack")),
                List.of(new TypeRequest(1, "Flying"))
        );
        assertInstanceOf(Pokemon.class, PokemonConverter.dtoToEntity(pokemonDTO));
    }
}