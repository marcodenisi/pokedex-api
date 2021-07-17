package md.pokedexapi.mapper;

import md.pokedexapi.dto.pokeapi.FlavorEntry;
import md.pokedexapi.dto.pokeapi.PokeApiResponse;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PokemonInformationMapperTest {

    @Test
    public void shouldConvertToPokemonInformation() {
        // given
        final var apiResponse = PokeApiResponse.builder()
                .habitat("cave")
                .isLegendary(true)
                .name("ditto")
                .textEntries(List.of(
                        FlavorEntry.builder()
                                .flavorText("A text")
                                .language("en")
                                .build(),
                        FlavorEntry.builder()
                                .flavorText("Another text")
                                .language("en")
                                .build(),
                        FlavorEntry.builder()
                                .flavorText("Un testo in italiano")
                                .language("it")
                                .build()
                ))
                .build();

        // when
        final var info = PokemonInformationMapper.toPokemonInformation(apiResponse);

        // then
        assertThat(info).isNotNull();
        assertThat(info.getName()).isEqualTo("ditto");
        assertThat(info.getHabitat()).isEqualTo("cave");
        assertThat(info.isLegendary()).isTrue();
        assertThat(info.getDescription()).isEqualTo("A text");
    }

    @Test
    public void shouldConvertToPokemonInformationWithNonEngDesc() {
        // given
        final var apiResponse = PokeApiResponse.builder()
                .habitat("cave")
                .isLegendary(true)
                .name("ditto")
                .textEntries(List.of(
                        FlavorEntry.builder()
                                .flavorText("Un testo in italiano")
                                .language("it")
                                .build()
                ))
                .build();

        // when
        final var info = PokemonInformationMapper.toPokemonInformation(apiResponse);

        // then
        assertThat(info).isNotNull();
        assertThat(info.getName()).isEqualTo("ditto");
        assertThat(info.getHabitat()).isEqualTo("cave");
        assertThat(info.isLegendary()).isTrue();
        assertThat(info.getDescription()).isNull();
    }

    @Test
    public void shouldNotConvertToPokemonInfoWhenInputNull() {
        // given, when, then
        assertThat(PokemonInformationMapper.toPokemonInformation(null)).isNull();
    }

}