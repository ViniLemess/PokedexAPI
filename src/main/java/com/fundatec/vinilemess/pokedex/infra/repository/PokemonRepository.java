package com.fundatec.vinilemess.pokedex.infra.repository;

import com.fundatec.vinilemess.pokedex.domain.entity.Pokemon;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface PokemonRepository extends MongoRepository<Pokemon, String> {

    Optional<Pokemon> findPokemonByName(String name);

    Optional<Pokemon> findPokemonByPokedexId(Long id);

    int countPokemonByNameOrPokedexId(String name, Integer id);

}
