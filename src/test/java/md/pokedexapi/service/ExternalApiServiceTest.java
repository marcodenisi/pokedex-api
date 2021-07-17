package md.pokedexapi.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.io.IOException;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ExternalApiServiceTest {

    private static AutoCloseable closeable;

    private ExternalApiService service;

    @Mock
    private ExchangeFunction pokeapiEF;
    @Mock
    private ExchangeFunction shakespeareEF;
    @Mock
    private ExchangeFunction yodaEF;

    @BeforeEach
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);

        WebClient pokeapiClient = WebClient.builder()
                .exchangeFunction(pokeapiEF)
                .build();
        WebClient shakespeareClient = WebClient.builder()
                .exchangeFunction(shakespeareEF)
                .build();
        WebClient yodaClient = WebClient.builder()
                .exchangeFunction(yodaEF)
                .build();
        service = new ExternalApiService(pokeapiClient, shakespeareClient, yodaClient);
    }

    @AfterEach
    public void teardown() throws Exception {
        closeable.close();
    }

    @Test
    public void shouldGetInformationByName() throws IOException {
        //given
        when(pokeapiEF.exchange(any())).thenReturn(Mono.just(
                ClientResponse.create(HttpStatus.OK)
                        .body(fromFile("fixtures/pokeapi_200.json"))
                        .header(HttpHeaders.CONTENT_TYPE, "application/json")
                        .build()
        ));

        // when
        final var info = service.getPokemonInformationByName("ditto");

        // then
        assertThat(info).isNotNull();
        assertThat(info.getName()).isEqualTo("ditto");
        assertThat(info.getHabitat()).isEqualTo("urban");
        assertThat(info.getTextEntries()).isNotEmpty().hasSize(2);
        assertThat(info.isLegendary()).isFalse();
    }

    @Test
    public void shouldHandlePokemonNotFound() {
        // given
        when(pokeapiEF.exchange(any())).thenReturn(Mono.just(
                ClientResponse.create(HttpStatus.NOT_FOUND)
                        .build()
        ));

        // when, then
        assertThrows(
                WebClientResponseException.NotFound.class,
                () -> service.getPokemonInformationByName("ditto")
        );
    }

    @Test
    public void shouldHandlePokemonApiErrors() {
        // given
        when(pokeapiEF.exchange(any())).thenReturn(Mono.just(
                ClientResponse.create(HttpStatus.BAD_REQUEST)
                        .build()
        ));

        // when, then
        assertThrows(
                WebClientResponseException.BadRequest.class,
                () -> service.getPokemonInformationByName("ditto")
        );
    }

    @Test
    public void shouldGetShakespeareTranslation() throws IOException {
        // given
        when(shakespeareEF.exchange(any())).thenReturn(Mono.just(
                ClientResponse.create(HttpStatus.OK)
                        .body(fromFile("fixtures/shakespeare_200.json"))
                        .header(HttpHeaders.CONTENT_TYPE, "application/json")
                        .build()
        ));

        // when
        final var translation = service.getShakespeareTranslation("a value");

        // then
        assertThat(translation).isNotNull().isNotEmpty().isEqualTo(
                "Thee did giveth mr. Tim a hearty meal,  but unfortunately what he did doth englut did maketh him kicketh the bucket."
        );
    }

    @Test
    public void shouldGetYodaTranslation() throws IOException {
        // given
        when(yodaEF.exchange(any())).thenReturn(Mono.just(
                ClientResponse.create(HttpStatus.OK)
                        .body(fromFile("fixtures/yoda_200.json"))
                        .header(HttpHeaders.CONTENT_TYPE, "application/json")
                        .build()
        ));

        // when
        final var translation = service.getYodaTranslation("another value");

        // then
        assertThat(translation).isNotNull().isNotEmpty().isEqualTo("Lost a planet,  master obiwan has.");
    }

    @Test
    public void shouldHandleTranslationServiceErrors() {
        // given
        when(shakespeareEF.exchange(any())).thenReturn(Mono.just(
                ClientResponse.create(HttpStatus.TOO_MANY_REQUESTS).build())
        );

        // when, then
        assertThrows(
                WebClientResponseException.TooManyRequests.class,
                () -> service.getShakespeareTranslation("should get 429")
        );
    }

    @Test
    public void shouldHandleTranslatingNullDescriptions() {
        // given, when, then
        assertThat(service.getShakespeareTranslation(null)).isNull();
        assertThat(service.getYodaTranslation(null)).isNull();
    }

    private String fromFile(final String fileName) throws IOException {
        return new String(getClass().getClassLoader().getResourceAsStream(fileName).readAllBytes());
    }

}