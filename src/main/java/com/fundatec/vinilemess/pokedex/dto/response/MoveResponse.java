package com.fundatec.vinilemess.pokedex.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MoveResponse {

    @JsonProperty("move")
    private MoveDetailsResponse move;

    public MoveResponse(String moveName) {
        this.move = new MoveDetailsResponse(moveName);
    }
}
