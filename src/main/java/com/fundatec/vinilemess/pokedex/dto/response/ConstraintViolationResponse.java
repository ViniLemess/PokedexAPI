package com.fundatec.vinilemess.pokedex.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fundatec.vinilemess.pokedex.dto.ConstraintViolationDTO;

import java.time.LocalDateTime;
import java.util.List;

public class ConstraintViolationResponse {

    private List<ConstraintViolationDTO> errors;

    private LocalDateTime timestamp;

    public ConstraintViolationResponse(List<ConstraintViolationDTO> errors) {
        this.errors = errors;
        this.timestamp = LocalDateTime.now();
    }

    public List<ConstraintViolationDTO> getErrors() {
        return this.errors;
    }

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
