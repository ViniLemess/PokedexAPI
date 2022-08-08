package com.fundatec.vinilemess.pokedex.converter;

import com.fundatec.vinilemess.pokedex.domain.Move;
import com.fundatec.vinilemess.pokedex.domain.Pokemon;
import com.fundatec.vinilemess.pokedex.domain.Type;
import com.fundatec.vinilemess.pokedex.service.dto.MoveDTO;
import com.fundatec.vinilemess.pokedex.service.dto.PokemonDTO;
import com.fundatec.vinilemess.pokedex.service.dto.TypeDTO;
import com.fundatec.vinilemess.pokedex.service.external.MoveResponse;
import com.fundatec.vinilemess.pokedex.service.external.PokemonResponse;
import com.fundatec.vinilemess.pokedex.service.external.TypeResponse;
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
        assertInstanceOf(PokemonDTO.class, PokemonConverter.responseToDto(pokemonResponse));
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
        assertInstanceOf(PokemonDTO.class, PokemonConverter.entityToDto(pokemon));
    }

    @Test
    void mustConvertFromDtoToEntitySuccessfully() {
        var pokemonDTO = new PokemonDTO(
                1,
                "Testemon",
                4,
                40,
                List.of(new MoveDTO("TestAtack")),
                List.of(new TypeDTO(1, "Flying"))
        );
        assertInstanceOf(Pokemon.class, PokemonConverter.dtoToEntity(pokemonDTO));
    }
}