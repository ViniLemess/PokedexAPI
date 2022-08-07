package com.fundatec.vinilemess.pokedex.service.dto;

import lombok.Getter;

import javax.validation.Valid;
import javax.validation.constraints.*;
import java.util.List;

@Getter
public class PokemonDTO {

    @Positive
    @Min(value = 906)
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
    private List<@Valid MoveDTO> moves;

    @NotEmpty
    @Size(max = 2)
    private List<@Valid TypeDTO> types;

    public PokemonDTO() {}

    public PokemonDTO(Integer pokedexId, String name, Integer weight, Integer height, List<MoveDTO> moves, List<TypeDTO> types) {
        this.pokedexId = pokedexId;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.moves = moves;
        this.types = types;
    }
}
