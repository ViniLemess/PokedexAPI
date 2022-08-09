package com.fundatec.vinilemess.pokedex.rest.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

public interface IHealthCheckerController {

    @ApiOperation(
            httpMethod = "GET",
            value = "HealthChecker",
            notes = "Return running message and 200 if api is running")
    ResponseEntity<String> healthCheck();
}
