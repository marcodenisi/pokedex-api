package md.pokedexapi.service;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import md.pokedexapi.dto.pokeapi.PokeApiResponse;
import md.pokedexapi.dto.translatorapi.TranslatorRequest;
import md.pokedexapi.dto.translatorapi.TranslatorResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
@Slf4j
public class ExternalApiService {

    private final WebClient pokeapiClient;
    private final WebClient shakespeareClient;
    private final WebClient yodaClient;

    public PokeApiResponse getPokemonInformationByName(final String name) {
        log.info("Calling pokeapi service with pokemon name {}", name);

        return pokeapiClient.get()
                .uri(builder -> builder.path(name).build())
                .retrieve()
                .bodyToMono(PokeApiResponse.class)
                .block();
    }

    public String getShakespeareTranslation(final String value) {
        log.info("Calling shakespeare translation service with value {}", value);

        return getTranslation(value, shakespeareClient);
    }

    public String getYodaTranslation(final String value) {
        log.info("Calling shakespeare translation service with value {}", value);

        return getTranslation(value, yodaClient);
    }

    private String getTranslation(final String value, final WebClient client) {
        if (value == null) {
            return null;
        }

        return client.post()
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(TranslatorRequest.builder().text(value).build()), TranslatorRequest.class)
                .retrieve()
                .bodyToMono(TranslatorResponse.class)
                .map(TranslatorResponse::getTranslated)
                .block();
    }

}
