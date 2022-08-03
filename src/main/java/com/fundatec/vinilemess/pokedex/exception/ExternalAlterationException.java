package com.fundatec.vinilemess.pokedex.exception;

public class ExternalAlterationException extends RuntimeException {
    public ExternalAlterationException() {
        super("Cannot delete external pokemon!");
    }
}
