package com.fundatec.vinilemess.pokedex.rest.controller.impl;

import com.fundatec.vinilemess.pokedex.domain.entity.Pokemon;
import com.fundatec.vinilemess.pokedex.rest.controller.IPokemonController;
import com.fundatec.vinilemess.pokedex.service.IPokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/pokemon")
public class PokemonController implements IPokemonController {

    private final IPokemonService pokemonService;

    @Autowired
    public PokemonController(IPokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pokemon> getPokemonById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(pokemonService.getPokemonById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<Pokemon> getPokemonByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(pokemonService.getPokemonByName(name));
    }

    @Override
    public ResponseEntity<Iterable<Pokemon>> getHeavyPokemons() {
        return null;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerPokemon(@RequestBody @Valid Pokemon pokemon) {
        pokemonService.registerPokemon(pokemon);
    }

    @DeleteMapping
    public void deletePokemon(String name) {
        pokemonService.deletePokemon(name);
    }

    @PutMapping
    public void updatePokemon(Pokemon pokemon) {
        pokemonService.updatePokemon(pokemon);
    }
}
