package md.pokedexapi.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import md.pokedexapi.dto.PokemonInformation;
import md.pokedexapi.exception.PokeapiClientException;
import md.pokedexapi.exception.PokeapiServerException;
import md.pokedexapi.mapper.PokemonInformationMapper;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;

@Component
@RequiredArgsConstructor
@Slf4j
public class PokemonService {

    private final ExternalApiService externalApiService;

    public PokemonInformation getPokemonInformationByName(final String name) throws PokeapiClientException, PokeapiServerException {
        log.info("retrieving info for pokemon with name {}", name);

        try {

            return PokemonInformationMapper.toPokemonInformation(
                    externalApiService.getPokemonInformationByName(name)
            );
        } catch (WebClientResponseException ex) {

            if (ex.getStatusCode().is4xxClientError()) {

                log.error("Client error while calling pokeapi", ex);
                throw new PokeapiClientException("Client error while calling pokeapi", ex);
            } else if (ex.getStatusCode().is5xxServerError()) {

                log.error("Server error while calling pokeapi", ex);
                throw new PokeapiServerException("Server error while calling pokeapi", ex);
            }

            throw ex;
        }
    }

    public PokemonInformation getTranslatedPokemonInformationByName(final String name)
            throws PokeapiClientException, PokeapiServerException {
        log.info("retrieving translated info for pokemon with name {}", name);

        final var info = getPokemonInformationByName(name);

        try {

            final var description = shouldBeYodaTranslated(info) ?
                    externalApiService.getYodaTranslation(info.getDescription()) :
                    externalApiService.getShakespeareTranslation(info.getDescription());

            return info.toBuilder().description(description).build();
        } catch (WebClientResponseException ex) {

            log.warn("Exception while calling translation api, returning non translated desc", ex);
            return info;
        }
    }

    private static boolean shouldBeYodaTranslated(final PokemonInformation information) {
        return "cave".equals(information.getHabitat()) || information.isLegendary();
    }

}
