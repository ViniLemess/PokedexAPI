package com.fundatec.vinilemess.pokedex.dto.request;

import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Getter
public class PokemonRequest {

    @Positive
    private Integer pokedexId;

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private Integer weight;

    @NotNull
    @Positive
    private Integer height;

    @NotEmpty
    private List<@Valid MoveRequest> moves;

    @NotEmpty
    @Size(max = 2)
    private List<@Valid TypeRequest> types;

    public PokemonRequest() {}

    public PokemonRequest(Integer pokedexId, String name, Integer weight, Integer height, List<MoveRequest> moves, List<TypeRequest> types) {
        this.pokedexId = pokedexId;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.moves = moves;
        this.types = types;
    }
}
