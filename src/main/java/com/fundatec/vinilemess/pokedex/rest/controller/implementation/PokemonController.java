package com.fundatec.vinilemess.pokedex.rest.controller.implementation;

import com.fundatec.vinilemess.pokedex.dto.response.PokemonResponse;
import com.fundatec.vinilemess.pokedex.rest.controller.IPokemonController;
import com.fundatec.vinilemess.pokedex.service.IPokemonService;
import com.fundatec.vinilemess.pokedex.dto.request.PokemonRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/pokemon")
public class PokemonController implements IPokemonController {

    private final IPokemonService pokemonService;

    @Autowired
    public PokemonController(IPokemonService pokemonService) {
        this.pokemonService = pokemonService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PokemonResponse> getPokemonById(@PathVariable("id") Integer id) {
        return ResponseEntity.ok(pokemonService.getPokemonById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<PokemonResponse> getPokemonByName(@PathVariable("name") String name) {
        return ResponseEntity.ok(pokemonService.getPokemonByName(name));
    }

    @GetMapping("/filter")
    public ResponseEntity<List<PokemonResponse>> getPokemonsByWeight(@RequestParam("weight") Integer weight) {
        return ResponseEntity.ok(pokemonService.getPokemonsByWeight(weight));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void registerPokemon(@RequestBody @Valid PokemonRequest pokemon) {
        pokemonService.registerPokemon(pokemon);
    }

    @DeleteMapping("/{name}")
    public void deletePokemon(@PathVariable("name") String name) {
        pokemonService.deletePokemon(name);
    }

    @PutMapping
    public void updatePokemon(@RequestBody @Valid PokemonRequest pokemon) {
        pokemonService.updatePokemon(pokemon);
    }
}
