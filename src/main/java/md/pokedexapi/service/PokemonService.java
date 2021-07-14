package md.pokedexapi.service;

import lombok.RequiredArgsConstructor;
import md.pokedexapi.dto.PokemonInformation;
import md.pokedexapi.dto.pokeapi.PokeApiResponse;
import md.pokedexapi.mapper.PokemonInformationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PokemonService {

    public static final Logger LOGGER = LoggerFactory.getLogger(PokemonService.class);

    private final ExternalApiService externalApiService;

    public PokemonInformation getPokemonInformationByName(final String name) {
        LOGGER.info("retrieving info for pokemon with name {}", name);

        return PokemonInformationMapper.toPokemonInformation(
                externalApiService.getPokemonInformationByName(name)
        );
    }

    public PokemonInformation getTranslatedPokemonInformationByName(final String name) {
        LOGGER.info("retrieving translated info for pokemon with name {}", name);

        final var info = externalApiService.getPokemonInformationByName(name);
        return PokemonInformationMapper.toPokemonInformation(
                info,
                shouldBeYodaTranslated(info) ? externalApiService.getYodaTranslation(null) : externalApiService.getShakespeareTranslation(null)
        );
    }

    private static boolean shouldBeYodaTranslated(final PokeApiResponse response) {
        return false;
    }

}
