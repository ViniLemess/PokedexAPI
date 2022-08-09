package com.fundatec.vinilemess.pokedex.rest.handler.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;

public class ConstraintViolationsDTO {

    private List<ConstraintViolationError> errors;

    private LocalDateTime timestamp;

    public ConstraintViolationsDTO(List<ConstraintViolationError> errors) {
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
    }

    public List<ConstraintViolationError> getErrors() {
        return this.errors;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
