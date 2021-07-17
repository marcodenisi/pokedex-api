package md.pokedexapi.exception;

public class PokeapiClientException extends Exception {

    public PokeapiClientException(Throwable cause) {
        super(cause);
    }

    public PokeapiClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public PokeapiClientException(String message) {
        super(message);
    }
}
