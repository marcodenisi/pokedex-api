package md.pokedexapi.service;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

class PokemonServiceTest {

    private AutoCloseable closeable;

    @InjectMocks
    private PokemonService service;

    @Mock
    private ExternalApiService externalApiService;

    @BeforeAll
    public void setup() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @AfterAll
    public void teardown() throws Exception {
        closeable.close();
    }

    @Test
    public void shouldReturnInformationByName() {}

    @Test
    public void shouldReturnTranslatedInformationByName() {}

    @Test
    public void shouldThrowNotFoundWhenPokemonNotFound() {}

    @Test
    public void shouldThrowExceptionWhenExternalServiceReturnException() {}

}