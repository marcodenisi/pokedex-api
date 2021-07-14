package md.pokedexapi;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class PokedexApiConfiguration {

    @Value("${external.api.poke.url}")
    private String pokeApiUrl;

    @Value("${external.api.shakespeare.url}")
    private String shakespeareUrl;

    @Value("${external.api.yoda.url}")
    private String yodaUrl;

    @Bean("pokeapiClient")
    public WebClient pokeapiClient() {
        return WebClient.create(pokeApiUrl);
    }

    @Bean("shakespeareClient")
    public WebClient shakespeareClient() {
        return WebClient.create(shakespeareUrl);
    }

    @Bean("yodaClient")
    public WebClient yodaClient() {
        return WebClient.create(yodaUrl);
    }

}
