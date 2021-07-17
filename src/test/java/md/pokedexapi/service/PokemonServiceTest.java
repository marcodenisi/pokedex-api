package md.pokedexapi.service;

import md.pokedexapi.dto.pokeapi.FlavorEntry;
import md.pokedexapi.dto.pokeapi.PokeApiResponse;
import md.pokedexapi.exception.PokeapiClientException;
import md.pokedexapi.exception.PokeapiServerException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClientResponseException;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PokemonServiceTest {

    private AutoCloseable closeable;

    @InjectMocks
    private PokemonService service;

    @Mock
    private ExternalApiService externalApiService;

    @BeforeEach
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterEach
    public void teardown() throws Exception {
        closeable.close();
    }

    @Test
    public void shouldReturnInformationByName() throws PokeapiClientException, PokeapiServerException {
        // given
        when(externalApiService.getPokemonInformationByName(any())).thenReturn(
                PokeApiResponse.builder()
                        .habitat("street")
                        .isLegendary(true)
                        .name("ditto")
                        .textEntries(List.of(
                                FlavorEntry.builder()
                                        .flavorText("An english description text")
                                        .language("en")
                                        .build()
                        ))
                        .build()
        );

        // when
        final var ditto = service.getPokemonInformationByName("ditto");

        // then
        assertThat(ditto).isNotNull();
        assertThat(ditto.getName()).isEqualTo("ditto");
        assertThat(ditto.getHabitat()).isEqualTo("street");
        assertThat(ditto.isLegendary()).isTrue();
        assertThat(ditto.getDescription()).isEqualTo("An english description text");
    }

    @Test
    public void gettingInfoshouldThrowNotFoundWhenPokemonNotFound() {
        // given
        when(externalApiService.getPokemonInformationByName(any())).thenThrow(
                WebClientResponseException.create(404, "404", null, null, null)
        );

        // when, then
        assertThrows(PokeapiClientException.class, () -> service.getPokemonInformationByName("ditto"));
    }

    @Test
    public void gettingInfoshouldThrowExceptionWhenExternalServiceReturnException() {
        // given
        when(externalApiService.getPokemonInformationByName(any())).thenThrow(
                WebClientResponseException.create(500, "500", null, null, null)
        );

        // when, then
        assertThrows(PokeapiServerException.class, () -> service.getPokemonInformationByName("ditto"));
    }

    @Test
    public void shouldReturnYodaTranslatedInformationByName() throws PokeapiClientException, PokeapiServerException {
        // given
        when(externalApiService.getPokemonInformationByName(any())).thenReturn(
                PokeApiResponse.builder()
                        .habitat("cave")
                        .isLegendary(true)
                        .name("ditto")
                        .textEntries(List.of(
                                FlavorEntry.builder()
                                        .flavorText("An english description text")
                                        .language("en")
                                        .build()
                        ))
                        .build()
        );
        when(externalApiService.getYodaTranslation(any())).thenReturn("Translated string");

        // when
        final var ditto = service.getTranslatedPokemonInformationByName("ditto");

        // then
        assertThat(ditto).isNotNull();
        assertThat(ditto.getDescription()).isEqualTo("Translated string");
        verify(externalApiService, never()).getShakespeareTranslation(any());
        verify(externalApiService, times(1)).getYodaTranslation(any());
    }

    @Test
    public void shouldReturnShakespeareTranslatedInformationByName() throws PokeapiClientException, PokeapiServerException {
        // given
        when(externalApiService.getPokemonInformationByName(any())).thenReturn(
                PokeApiResponse.builder()
                        .habitat("street")
                        .isLegendary(false)
                        .name("ditto")
                        .textEntries(List.of(
                                FlavorEntry.builder()
                                        .flavorText("An english description text")
                                        .language("en")
                                        .build()
                        ))
                        .build()
        );
        when(externalApiService.getShakespeareTranslation(any())).thenReturn("SS Translated string");

        // when
        final var ditto = service.getTranslatedPokemonInformationByName("ditto");

        // then
        assertThat(ditto).isNotNull();
        assertThat(ditto.getDescription()).isEqualTo("SS Translated string");
        verify(externalApiService, times(1)).getShakespeareTranslation(any());
        verify(externalApiService, never()).getYodaTranslation(any());
    }

    @Test
    public void translatedServiceShouldReturnOriginalDescDueToServerError() throws PokeapiClientException, PokeapiServerException {
        // given
        when(externalApiService.getPokemonInformationByName(any())).thenReturn(
                PokeApiResponse.builder()
                        .habitat("street")
                        .isLegendary(false)
                        .name("ditto")
                        .textEntries(List.of(
                                FlavorEntry.builder()
                                        .flavorText("An english description text")
                                        .language("en")
                                        .build()
                        ))
                        .build()
        );
        when(externalApiService.getShakespeareTranslation(any())).thenThrow(
                WebClientResponseException.create(500, "500", null, null, null)
        );

        // when
        final var ditto = service.getTranslatedPokemonInformationByName("ditto");

        // then
        assertThat(ditto).isNotNull();
        assertThat(ditto.getDescription()).isEqualTo("An english description text");
        verify(externalApiService, times(1)).getShakespeareTranslation(any());
        verify(externalApiService, never()).getYodaTranslation(any());
    }

}