package com.fundatec.vinilemess.pokedex.domain;

import javax.validation.constraints.NotBlank;

public class Move {

    @NotBlank
    private String name;

    public Move(String name) {
        this.name = name;
    }

    public Move(){}

    public String getName() {
        return name;
    }
}
