package md.pokedexapi.service;

import lombok.AllArgsConstructor;
import md.pokedexapi.dto.pokeapi.PokeApiResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@AllArgsConstructor
public class ExternalApiService {

    private final WebClient pokeapiClient;
    private final WebClient shakespeareClient;
    private final WebClient yodaClient;

    public PokeApiResponse getPokemonInformationByName(final String name) {
        return null;
    }

    public String getShakespeareTranslation(final String value) {
        return null;
    }

    public String getYodaTranslation(final String value) {
        return null;
    }

}
