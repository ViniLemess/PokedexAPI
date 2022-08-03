package com.fundatec.vinilemess.pokedex.exception;

public class DuplicatedPokemonException extends RuntimeException {
    public DuplicatedPokemonException(String message) {
        super(message);
    }
}
