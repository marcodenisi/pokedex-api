package md.pokedexapi.dto.pokeapi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FlavorEntry {

    @JsonProperty("flavor_text")
    private String flavorText;
    @JsonIgnore
    private String language;

    @JsonProperty("language")
    public void setLanguage(final Map<String, String> languageMap) {
        if (languageMap != null) {
            this.language = languageMap.get("name");
        }
    }

}
