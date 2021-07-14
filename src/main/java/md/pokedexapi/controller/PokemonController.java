package md.pokedexapi.controller;

import lombok.AllArgsConstructor;
import md.pokedexapi.dto.PokemonInformation;
import md.pokedexapi.service.PokemonService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pokemon")
@AllArgsConstructor
public class PokemonController {

    private final PokemonService service;

    @GetMapping("/{name}")
    public PokemonInformation getPokemonInformationByName(@PathVariable final String name) {
        return service.getPokemonInformationByName(name);
    }

    @GetMapping("/translated/{name}")
    public PokemonInformation getTranslatedPokemonInformationByName(@PathVariable final String name) {
        return service.getTranslatedPokemonInformationByName(name);
    }

}
