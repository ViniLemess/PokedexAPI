package com.fundatec.vinilemess.pokedex.service.implementation;

import com.fundatec.vinilemess.pokedex.client.IPokemonApiClient;
import com.fundatec.vinilemess.pokedex.dto.request.PokemonRequest;
import com.fundatec.vinilemess.pokedex.dto.response.PokemonResponse;
import com.fundatec.vinilemess.pokedex.entity.Pokemon;
import com.fundatec.vinilemess.pokedex.exception.DuplicatedPokemonException;
import com.fundatec.vinilemess.pokedex.exception.ExternalAlterationException;
import com.fundatec.vinilemess.pokedex.exception.PokemonNotFoundException;
import com.fundatec.vinilemess.pokedex.mapper.MoveMapper;
import com.fundatec.vinilemess.pokedex.mapper.PokemonMapper;
import com.fundatec.vinilemess.pokedex.mapper.TypeMapper;
import com.fundatec.vinilemess.pokedex.repository.PokemonRepository;
import com.fundatec.vinilemess.pokedex.service.IPokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

import static com.fundatec.vinilemess.pokedex.mapper.PokemonMapper.requestToEntity;

@Service
public class PokemonService implements IPokemonService {

    public static final String POKEMON = "Pokemon ";
    private final IPokemonApiClient pokemonApiClient;
    private final PokemonRepository repository;

    @Autowired
    public PokemonService(IPokemonApiClient pokemonApiClient, PokemonRepository repository) {
        this.pokemonApiClient = pokemonApiClient;
        this.repository = repository;
    }

    @Override
    public PokemonResponse getPokemonById(Integer id) {
        return repository.findPokemonByPokedexIdAndDeletedFalse(id)
                .map(PokemonMapper::entityToResponse)
                .orElseGet(() -> pokemonApiClient.findPokemonResponseById(id));
    }

    @Override
    public PokemonResponse getPokemonByName(String name) {
        return repository.findPokemonByNameAndDeletedFalse(name)
                .map(PokemonMapper::entityToResponse)
                .orElseGet(() -> pokemonApiClient.findPokemonResponseByName(name));
    }

    @Override
    public List<PokemonResponse> getPokemonsByWeight(Integer weight) {
        return repository.findPokemonsByWeightGreaterThanAndDeletedFalse(weight)
                .stream()
                .map(PokemonMapper::entityToResponse)
                .toList();
    }

    @Override
    public Pokemon registerPokemon(PokemonRequest pokemonRequest) {
        Pokemon pokemon = requestToEntity(pokemonRequest);

        return repository.save(findDeletedPokemon(pokemon)
                .map(mapToRestoredPokemon(pokemon))
                .orElseGet(saveNewPokemon(pokemon)));
    }

    @Override
    public void deletePokemon(String name) {
        validateCanonicalPokemon(name);
        Pokemon pokemon = repository.findPokemonByNameAndDeletedFalse(name)
                .orElseThrow(() -> new PokemonNotFoundException(POKEMON + name));
        pokemon.markDeleted();
        repository.save(pokemon);
    }

    @Override
    public void updatePokemon(PokemonRequest pokemonRequest) {
        validateCanonicalPokemon(pokemonRequest.getName());
        repository.save(generateUpdatedPokemon(pokemonRequest));
    }

    private Function<Pokemon, Pokemon> mapToRestoredPokemon(Pokemon pokemon) {
        return restoredPokemon -> {
            restoredPokemon.restorePokemon();
            restoredPokemon.setHeight(pokemon.getHeight());
            restoredPokemon.setWeight(pokemon.getWeight());
            restoredPokemon.setTypes(pokemon.getTypes());
            restoredPokemon.setMoves(pokemon.getMoves());
            return restoredPokemon;
        };
    }

    private Supplier<Pokemon> saveNewPokemon(Pokemon pokemon) {
        validatePokemonDuplicated(pokemon);
        return () -> repository.save(pokemon);
    }
    private Optional<Pokemon> findDeletedPokemon(Pokemon pokemon) {
        return repository.findPokemonByNameAndPokedexIdAndDeletedTrue(pokemon.getName(), pokemon.getPokedexId());
    }

    private Pokemon generateUpdatedPokemon(PokemonRequest pokemonRequest) {
        Pokemon presentPokemon = repository
                .findPokemonByNameAndDeletedFalse(pokemonRequest.getName()).orElseThrow(() ->
                        new PokemonNotFoundException(POKEMON + pokemonRequest.getName()));
        return Pokemon.builder()
                .id(presentPokemon.getId())
                .name(presentPokemon.getName())
                .pokedexId(presentPokemon.getPokedexId())
                .weight(pokemonRequest.getWeight())
                .height(pokemonRequest.getHeight())
                .moves(MoveMapper.requestsToEntities(pokemonRequest.getMoves()))
                .types(TypeMapper.requestsToEntities(pokemonRequest.getTypes()))
                .build();
    }

    private void validateCanonicalPokemon(String name) {
        if (pokemonApiClient.validatePokemonExistenceByName(name))
            throw new ExternalAlterationException("Cannot alterate canonical pokemon!");
    }

    private void validatePokemonDuplicated(Pokemon pokemon) {
        boolean existsExternally = pokemonApiClient.validatePokemonExistenceByName(pokemon.getName());
        boolean existsInternally = repository.countPokemonByNameAndDeletedFalseOrPokedexIdAndDeletedFalse(pokemon.getName(), pokemon.getPokedexId()) > 0;
        if (existsExternally || existsInternally) throw new DuplicatedPokemonException("This pokemon already exists!");
    }
}
