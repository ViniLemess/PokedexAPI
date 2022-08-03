package com.fundatec.vinilemess.pokedex.exception;

public class PokemonNotFoundException extends RuntimeException{
    public PokemonNotFoundException(String notFoundObject) {
        super("Could not found : " + notFoundObject);
    }
}
