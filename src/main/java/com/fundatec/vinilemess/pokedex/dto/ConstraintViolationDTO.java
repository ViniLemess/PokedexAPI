package com.fundatec.vinilemess.pokedex.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ConstraintViolationDTO {
    private String field;
    private String message;
}
