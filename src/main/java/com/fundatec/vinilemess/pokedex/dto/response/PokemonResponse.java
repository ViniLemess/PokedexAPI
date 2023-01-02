package com.fundatec.vinilemess.pokedex.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter

public class PokemonResponse {

    @JsonProperty("id")
    private Integer id;

    @JsonProperty("name")

    private String name;

    @JsonProperty("height")

    private Integer height;

    @JsonProperty("weight")

    private Integer weight;

    @JsonProperty("moves")

    private List<MoveResponse> moves;

    @JsonProperty("types")

    private List<TypeResponse> types;

}
