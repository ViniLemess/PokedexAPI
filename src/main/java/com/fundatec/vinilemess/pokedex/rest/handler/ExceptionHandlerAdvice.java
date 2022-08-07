package com.fundatec.vinilemess.pokedex.rest.handler;

import com.fundatec.vinilemess.pokedex.exception.DuplicatedPokemonException;
import com.fundatec.vinilemess.pokedex.exception.PokemonNotFoundException;
import com.fundatec.vinilemess.pokedex.rest.handler.dto.ConstraintViolationError;
import com.fundatec.vinilemess.pokedex.rest.handler.dto.ConstraintViolationsDTO;
import com.fundatec.vinilemess.pokedex.rest.handler.dto.ErrorDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.IOException;
import java.time.LocalDateTime;
import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;
import static org.springframework.http.HttpStatus.Series.SERVER_ERROR;

@RestControllerAdvice
public class ExceptionHandlerAdvice implements ResponseErrorHandler {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {
        return (response.getStatusCode().series() == CLIENT_ERROR ||
                response.getStatusCode().series() == SERVER_ERROR);
    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {
        if (response.getStatusCode().series() == CLIENT_ERROR
                && response.getStatusCode() == HttpStatus.NOT_FOUND)
                    throw new PokemonNotFoundException("Pokemon");
    }

    @ExceptionHandler(PokemonNotFoundException.class)
    public ResponseEntity<ErrorDTO> notFoundClientHandler(PokemonNotFoundException exception)  {
        logger.error(exception.getMessage());
        return new ResponseEntity<>(buildError(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Throwable.class)
    public ResponseEntity<ErrorDTO> unknownErrorHandler(Throwable exception) {
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(buildError("Something went wrong!"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DuplicatedPokemonException.class)
    public ResponseEntity<ErrorDTO> duplicatedPokemonHandler(DuplicatedPokemonException exception) {
        logger.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(buildError(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ConstraintViolationsDTO> methodArgumentNotValidHandler(MethodArgumentNotValidException exception) {
        logger.error(exception.getMessage());
        var constraintViolationsDTO = new ConstraintViolationsDTO(exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ConstraintViolationError(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(constraintViolationsDTO);
    }

    private ErrorDTO buildError(String message) {
        return new ErrorDTO(message, LocalDateTime.now());
    }
}
