package com.fundatec.vinilemess.pokedex.rest.controller;

import org.springframework.http.ResponseEntity;

public interface IHealthCheckerController {

    ResponseEntity<String> healthCheck();
}
