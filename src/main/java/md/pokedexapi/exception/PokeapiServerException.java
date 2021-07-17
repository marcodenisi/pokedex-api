package md.pokedexapi.exception;

public class PokeapiServerException extends Exception {

    public PokeapiServerException(String message) {
        super(message);
    }

    public PokeapiServerException(String message, Throwable cause) {
        super(message, cause);
    }
}
