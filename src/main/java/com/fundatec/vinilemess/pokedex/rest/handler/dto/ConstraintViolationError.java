package com.fundatec.vinilemess.pokedex.rest.handler.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class ConstraintViolationError {
    private String field;
    private String message;
}
