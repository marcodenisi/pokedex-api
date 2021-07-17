package md.pokedexapi.controller;

import md.pokedexapi.exception.PokeapiClientException;
import md.pokedexapi.exception.PokeapiServerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class PokemonControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler({PokeapiClientException.class, PokeapiServerException.class})
    public ResponseEntity<Object> handlePokeapiException(
            PokeapiClientException ex, WebRequest request) {

        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());

        return new ResponseEntity<>(body, HttpStatus.UNPROCESSABLE_ENTITY);
    }

}
