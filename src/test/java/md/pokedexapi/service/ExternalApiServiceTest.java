package md.pokedexapi.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.reactive.function.client.WebClient;

class ExternalApiServiceTest {

    private AutoCloseable closeable;

    @InjectMocks
    private ExternalApiService service;

    @Mock
    private WebClient pokeapiClient;
    @Mock
    private WebClient shakespeareClient;
    @Mock
    private WebClient yodaClient;

    @BeforeAll
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterAll
    public void teardown() throws Exception {
        closeable.close();
    }

    @Test
    public void shouldGetInformationByName() {
    }

    @Test
    public void shouldHandlePokemonNotFound() {
    }

    @Test
    public void shouldHandlePokemonApiErrors() {
    }

    @Test
    public void shouldGetShakespeareTranslation() {
    }

    @Test
    public void shouldGetYodaTranslation() {
    }

    @Test
    public void shouldHandleShakespeareServiceErrors() {
    }

    @Test
    public void shouldHandleYodaServiceErrors() {
    }

}