package com.fundatec.vinilemess.pokedex.service.implementation;

import com.fundatec.vinilemess.pokedex.converter.PokemonConverter;
import com.fundatec.vinilemess.pokedex.domain.Move;
import com.fundatec.vinilemess.pokedex.domain.Pokemon;
import com.fundatec.vinilemess.pokedex.domain.Type;
import com.fundatec.vinilemess.pokedex.exception.DuplicatedPokemonException;
import com.fundatec.vinilemess.pokedex.exception.ExternalAlterationException;
import com.fundatec.vinilemess.pokedex.exception.PokemonNotFoundException;
import com.fundatec.vinilemess.pokedex.infra.repository.PokemonRepository;
import com.fundatec.vinilemess.pokedex.service.IPokemonIntegrationService;
import com.fundatec.vinilemess.pokedex.service.IPokemonService;
import com.fundatec.vinilemess.pokedex.service.dto.PokemonDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public PokemonDTO getPokemonById(Integer id) {
        try {
            return responseToDto(pokemonIntegrationService.getPokemonResponseById(id));
        } catch (PokemonNotFoundException exception) {
            return entityToDto(repository.findPokemonByPokedexIdAndDeletedFalse(id).orElseThrow(() -> new PokemonNotFoundException("Pokemon " + id)));
        }
    }

    @Override
    public PokemonDTO getPokemonByName(String name) {
        try {
            return responseToDto(pokemonIntegrationService.getPokemonResponseByName(name));
        } catch (PokemonNotFoundException exception) {
            return entityToDto(repository.findPokemonByNameAndDeletedFalse(name).orElseThrow(() -> new PokemonNotFoundException("Pokemon " + name)));
        }
    }

    @Override
    public List<PokemonDTO> getPokemonsByWeight(Integer weight) {
        return repository.findPokemonsByWeightGreaterThanAndDeletedFalse(weight)
                .stream()
                .map(PokemonConverter::entityToDto)
                .toList();
    }

    @Override
    public Pokemon registerPokemon(PokemonDTO pokemonDTO) {
        Pokemon pokemon = dtoToEntity(pokemonDTO);
        isPokemonDuplicated(pokemon);
        return repository.save(pokemon);
    }

    @Override
    public void deletePokemon(String name) {
        if (pokemonIntegrationService.validatePokemonExistenceByName(name))
            throw new ExternalAlterationException("Cannot delete canonical pokemon!");
        var pokemon = repository.findPokemonByNameAndDeletedFalse(name).orElseThrow(() -> new PokemonNotFoundException("Pokemon " + name));
        pokemon.markDeleted();
        repository.save(pokemon);
    }

    @Override
    public void updatePokemon(PokemonDTO pokemonDTO) {
        if (pokemonIntegrationService.validatePokemonExistenceByName(pokemonDTO.getName()))
            throw new ExternalAlterationException("Cannot update canonical pokemon!");
        repository.save(generateUpdatedPokemon(pokemonDTO));
    }

    private Pokemon generateUpdatedPokemon(PokemonDTO pokemonDto) {
        var presentPokemon = repository
                .findPokemonByNameAndDeletedFalse(pokemonDto.getName()).orElseThrow(() ->
                        new PokemonNotFoundException("Pokemon " + pokemonDto.getName()));
        return new Pokemon(
                presentPokemon.getId(),
                presentPokemon.getPokedexId(),
                presentPokemon.getName(),
                pokemonDto.getWeight(),
                pokemonDto.getHeight(),
                pokemonDto.getMoves()
                        .stream()
                        .map(moveDTO -> new Move(moveDTO.getName()))
                        .toList(),
                pokemonDto.getTypes()
                        .stream()
                        .map(typeDTO -> new Type(typeDTO.getSlot(), typeDTO.getName()))
                        .toList()
        );
    }

    private void isPokemonDuplicated(Pokemon pokemon) {
        boolean existsExternally = pokemonIntegrationService.validatePokemonExistenceByName(pokemon.getName());
        boolean existsInternally = repository.countPokemonByNameAndDeletedFalseOrPokedexIdAndDeletedFalse(pokemon.getName(), pokemon.getPokedexId()) > 0;
        if (existsExternally || existsInternally) throw new DuplicatedPokemonException("This pokemon already exists!");
    }
}
