package com.fundatec.vinilemess.pokedex.service.dto;

import lombok.Getter;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Getter
public class TypeDTO {

    @Positive
    @Range(min = 1, max = 2)
    @NotNull
    private Integer slot;

    @NotBlank
    private String name;

    public TypeDTO() {}

    public TypeDTO(Integer slot, String name) {
        this.slot = slot;
        this.name = name;
    }
}
