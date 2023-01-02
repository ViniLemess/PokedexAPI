package com.fundatec.vinilemess.pokedex.dto.response;

public class MoveDetailsResponse {
    private String name;

    public MoveDetailsResponse(){}

    public MoveDetailsResponse(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
