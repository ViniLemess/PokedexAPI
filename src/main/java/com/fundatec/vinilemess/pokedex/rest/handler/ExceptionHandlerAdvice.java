package com.fundatec.vinilemess.pokedex.rest.handler;

import com.fundatec.vinilemess.pokedex.dto.ConstraintViolationDTO;
import com.fundatec.vinilemess.pokedex.dto.ErrorDTO;
import com.fundatec.vinilemess.pokedex.dto.response.ConstraintViolationResponse;
import com.fundatec.vinilemess.pokedex.exception.DuplicatedPokemonException;
import com.fundatec.vinilemess.pokedex.exception.ExternalAlterationException;
import com.fundatec.vinilemess.pokedex.exception.PokemonNotFoundException;
import feign.FeignException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionHandlerAdvice.class);

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

    @ExceptionHandler(FeignException.class)
    public ResponseEntity<ErrorDTO> feignClientExceptionHandler(FeignException exception) {
        logger.error(exception.getMessage());

        return switch (exception.status()) {
            case 404 -> {
                String searchKey = getSearchKey(exception.request().url());
                yield ResponseEntity.status(HttpStatus.valueOf(exception.status())).body(buildError("Could not found pokemon " + searchKey));
            }
            case 503 -> ResponseEntity.status(HttpStatus.valueOf(exception.status())).body(buildError("External service unavailable"));
            case 502 -> ResponseEntity.status(HttpStatus.valueOf(exception.status())).body(buildError("Bad Gateway"));
            default -> ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(buildError("Something went wrong!"));
        };
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ConstraintViolationResponse> methodArgumentNotValidHandler(MethodArgumentNotValidException exception) {
        logger.error(exception.getMessage());
        var constraintViolationsDTO = new ConstraintViolationResponse(exception
                .getBindingResult()
                .getFieldErrors()
                .stream()
                .map(fieldError -> new ConstraintViolationDTO(fieldError.getField(), fieldError.getDefaultMessage()))
                .toList());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(constraintViolationsDTO);
    }

    private ErrorDTO buildError(String message) {
        return new ErrorDTO(message, LocalDateTime.now());
    }

    private String getSearchKey(String url) {
        return url.substring(url.lastIndexOf("/") + 1);
    }
}
