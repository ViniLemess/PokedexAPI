package com.fundatec.vinilemess.pokedex.stubs;

import com.fundatec.vinilemess.pokedex.dto.response.MoveResponse;
import com.fundatec.vinilemess.pokedex.dto.response.PokemonResponse;
import com.fundatec.vinilemess.pokedex.dto.response.TypeResponse;
import com.fundatec.vinilemess.pokedex.entity.Move;
import com.fundatec.vinilemess.pokedex.entity.Pokemon;
import com.fundatec.vinilemess.pokedex.entity.Type;
import org.bson.types.ObjectId;

import java.util.List;

public class PokemonStub {

    public static PokemonResponse createCanonicalResponseStub() {
        return new PokemonResponse(
                132,
                "ditto",
                3,
                40,
                List.of(new MoveResponse("transform")),
                List.of(new TypeResponse(1, "normal"))
        );
    }

    public static Pokemon createFakemonStub() {
        return Pokemon.builder()
                .id(new ObjectId().toString())
                .pokedexId(1051)
                .name("Salenmon")
                .weight(5)
                .height(1)
                .moves(List.of(new Move("Bite"), new Move("Pursuit")))
                .types(List.of(new Type(1, "dark")))
                .build();
    }
}
