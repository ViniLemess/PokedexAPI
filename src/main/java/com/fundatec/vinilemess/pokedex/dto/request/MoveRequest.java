package com.fundatec.vinilemess.pokedex.dto.request;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class MoveRequest {

    @NotBlank
    private String name;

    public MoveRequest() {}

    public MoveRequest(String name) {
        this.name = name;
    }
}
