package com.fundatec.vinilemess.pokedex.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class TypeDetailsResponse {

    @JsonProperty("name")
    private String name;
}
