package md.pokedexapi.mapper;

import md.pokedexapi.dto.PokemonInformation;
import md.pokedexapi.dto.pokeapi.FlavorEntry;
import md.pokedexapi.dto.pokeapi.PokeApiResponse;

import java.util.Collections;
import java.util.Optional;

public class PokemonInformationMapper {

    public static PokemonInformation toPokemonInformation(final PokeApiResponse response) {
        if (response == null) {
            return null;
        }
        return PokemonInformation.builder()
                .description(getEnDescription(response))
                .isLegendary(response.isLegendary())
                .habitat(response.getHabitat())
                .name(response.getName())
                .build();
    }

    private static String getEnDescription(final PokeApiResponse response) {
        return Optional.ofNullable(response)
                .map(PokeApiResponse::getTextEntries)
                .orElse(Collections.emptyList())
                .stream()
                .filter(entry -> "en".equalsIgnoreCase(entry.getLanguage()))
                .map(FlavorEntry::getFlavorText)
                .findFirst()
                .map(PokemonInformationMapper::cleanDescription)
                .orElse(null);
    }

    private static String cleanDescription(final String description) {
        if (description == null) {
            return null;
        }
        return description.replaceAll("[\\n\\r\\t\\f]+", " ");
    }

}
