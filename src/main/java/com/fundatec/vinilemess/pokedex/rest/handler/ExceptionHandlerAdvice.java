package com.fundatec.vinilemess.pokedex.rest.handler;

import com.fundatec.vinilemess.pokedex.exception.DuplicatedPokemonException;
import com.fundatec.vinilemess.pokedex.exception.ExternalAlterationException;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

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
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorDTO> notFoundClientHandler(PokemonNotFoundException exception)  {
        logger.error(exception.getMessage());
        return new ResponseEntity<>(buildError(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ResponseEntity<ErrorDTO> unknownErrorHandler(Throwable exception) {
        logger.error(exception.getMessage(), exception);
        return new ResponseEntity<>(buildError("Something went wrong!"), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(DuplicatedPokemonException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ResponseEntity<ErrorDTO> duplicatedPokemonHandler(DuplicatedPokemonException exception) {
        logger.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(buildError(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorDTO> methodArgumentMismatchHandler(MethodArgumentTypeMismatchException exception) {
        logger.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(buildError("Invalid path parameter: " + exception.getValue()));
    }

    @ExceptionHandler(ExternalAlterationException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    public ResponseEntity<ErrorDTO> externalAlterationHandler(ExternalAlterationException exception) {
        logger.error(exception.getMessage());
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(buildError(exception.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
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
