package md.pokedexapi.dto.pokeapi;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class PokeApiResponse {

    @JsonProperty("name")
    private String name;
    @JsonProperty("flavor_text_entries")
    private List<FlavorEntry> textEntries;
    @JsonProperty("is_legendary")
    private boolean isLegendary;
    @JsonIgnore
    private String habitat;

    @JsonProperty("habitat")
    public void setHabitat(final Map<String, String> habitatMap) {
        if (habitatMap != null) {
            this.habitat = habitatMap.get("name");
        }
    }

}
