package com.fundatec.vinilemess.pokedex.entity;

import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

public class Type {

    @Positive
    @Range(min = 1, max = 2)
    @NotNull
    private Integer slot;

    @NotBlank
    private String name;

    public Type() {}

    public Type(Integer slot, String name) {
        this.slot = slot;
        this.name = name;
    }

    public Integer getSlot() {
        return slot;
    }

    public String getName() {
        return name;
    }
}
