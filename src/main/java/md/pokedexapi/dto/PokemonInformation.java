package md.pokedexapi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PokemonInformation {

    private String name;
    private String description;
    private String habitat;
    private boolean isLegendary;

}
