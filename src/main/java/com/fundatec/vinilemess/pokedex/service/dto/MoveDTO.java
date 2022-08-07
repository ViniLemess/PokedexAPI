package com.fundatec.vinilemess.pokedex.service.dto;

import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class MoveDTO {

    @NotBlank
    private String name;

    public MoveDTO() {}

    public MoveDTO(String name) {
        this.name = name;
    }
}
