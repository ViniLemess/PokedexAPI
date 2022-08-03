package com.fundatec.vinilemess.pokedex.service.implementation;

import com.fundatec.vinilemess.pokedex.domain.entity.Pokemon;
import com.fundatec.vinilemess.pokedex.exception.DuplicatedPokemonException;
import com.fundatec.vinilemess.pokedex.exception.ExternalAlterationException;
import com.fundatec.vinilemess.pokedex.exception.PokemonNotFoundException;
import com.fundatec.vinilemess.pokedex.infra.repository.PokemonRepository;
import com.fundatec.vinilemess.pokedex.service.IPokemonIntegrationService;
import com.fundatec.vinilemess.pokedex.service.IPokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static com.fundatec.vinilemess.pokedex.converter.PokemonConverter.*;

@Service
public class PokemonService implements IPokemonService {

    private final IPokemonIntegrationService pokemonIntegrationService;
    private final PokemonRepository repository;

    @Autowired
    public PokemonService(IPokemonIntegrationService pokemonIntegrationService, PokemonRepository repository) {
        this.pokemonIntegrationService = pokemonIntegrationService;
        this.repository = repository;
    }

    @Override
    public Pokemon getPokemonById(Long id) {
        try {
            return toEntity(pokemonIntegrationService.getPokemonResponseById(id));
        } catch (PokemonNotFoundException exception) {
            return repository.findPokemonByPokedexId(id).orElseThrow(() -> new PokemonNotFoundException("Pokemon " + id));
        }
    }

    @Override
    public Pokemon getPokemonByName(String name) {
        try {
            return toEntity(pokemonIntegrationService.getPokemonResponseByName(name));
        } catch (PokemonNotFoundException exception) {
            return repository.findPokemonByName(name).orElseThrow(() -> new PokemonNotFoundException("Pokemon " + name));
        }
    }

    @Override
    public Pokemon registerPokemon(Pokemon pokemon) {
        isPokemonDuplicated(pokemon);
        return repository.save(pokemon);
    }

    @Override
    public void deletePokemon(String name) {
        if (pokemonIntegrationService.validatePokemonExistenceByName(name))
            throw new ExternalAlterationException();
    }

    @Override
    public void updatePokemon(Pokemon pokemon) {

    }

    private void isPokemonDuplicated(Pokemon pokemon) {
        if (pokemonIntegrationService.validatePokemonExistenceByName(pokemon.getName()))
            throw new DuplicatedPokemonException(pokemon.getName() + " already exists externally!");
        else if (repository.countPokemonByNameOrPokedexId(pokemon.getName(), pokemon.getPokedexId()) > 0)
            throw new DuplicatedPokemonException("Pokemon with already exists!");
    }
}
