package com.fundatec.vinilemess.pokedex.rest.handler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ConstraintViolationDTO {
    private String field;
    private String message;
}
