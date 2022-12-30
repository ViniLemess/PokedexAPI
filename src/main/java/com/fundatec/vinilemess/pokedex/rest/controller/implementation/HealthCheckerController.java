package com.fundatec.vinilemess.pokedex.rest.controller.impl;

import com.fundatec.vinilemess.pokedex.rest.controller.IHealthCheckerController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/health")
public class HealthCheckerController implements IHealthCheckerController {

    @GetMapping
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("The application is running fine!");
    }
}
