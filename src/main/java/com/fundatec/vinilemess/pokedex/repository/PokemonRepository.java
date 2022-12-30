package com.fundatec.vinilemess.pokedex.infra.repository;

import com.fundatec.vinilemess.pokedex.entity.Pokemon;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface PokemonRepository extends MongoRepository<Pokemon, String> {

    Optional<Pokemon> findPokemonByNameAndDeletedFalse(String name);

    Optional<Pokemon> findPokemonByPokedexIdAndDeletedFalse(Integer id);

    int countPokemonByNameAndDeletedFalseOrPokedexIdAndDeletedFalse(String name, Integer id);

    List<Pokemon> findPokemonsByWeightGreaterThanAndDeletedFalse(Integer weight);

}
