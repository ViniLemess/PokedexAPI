package com.fundatec.vinilemess.pokedex.rest.controller;

import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.ResponseEntity;

public interface IHealthCheckerController {

    @Operation(
            method = "GET",
            summary = "HealthChecker",
            description = "Return running message and 200 if api is running")
    ResponseEntity<String> healthCheck();
}
