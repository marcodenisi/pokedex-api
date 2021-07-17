package md.pokedexapi.dto.translatorapi;

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
public class TranslatorResponse {

    @JsonIgnore
    private String translated;

    @JsonProperty("contents")
    private void setTranslated(final Map<String, String> contentsMap) {
        if (contentsMap != null) {
            this.translated = contentsMap.get("translated");
        }
    }

}
