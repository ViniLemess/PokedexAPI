package com.fundatec.vinilemess.pokedex.client.implementation;

import com.fundatec.vinilemess.pokedex.client.IPokemonApiClient;
import com.fundatec.vinilemess.pokedex.dto.response.PokemonResponse;
import feign.Feign;
import feign.Logger;
import feign.jackson.JacksonDecoder;
import feign.slf4j.Slf4jLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@Slf4j
public class PokeApiClient implements IPokemonApiClient {

    private final Client feignClient;

    public PokeApiClient(@Value("${poke-api-uri}") String pokeApiUri) {
        this.feignClient = buildClient(pokeApiUri);
    }

    public PokemonResponse findPokemonResponseById(Integer id) {
        log.info("Procurando pokemon externamente por id: {}", id);
        return feignClient.findPokemonById(id);
    }

    public PokemonResponse findPokemonResponseByName(String name) {
        log.info("Procurando pokemon externamente por nome: {}", name);
        return feignClient.findPokemonByName(name);
    }

    public boolean validatePokemonExistenceByName(String name) {
        try {
            feignClient.findPokemonByName(name);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    private Client buildClient(String uri) {
        return Feign.builder()
                .decoder(new JacksonDecoder())
                .contract(new SpringMvcContract())
                .logger(new Slf4jLogger())
                .logLevel(Logger.Level.FULL)
                .target(Client.class, uri);
    }

    @FeignClient
    private interface Client {

        @GetMapping(value = "/{pokemonName}")
        PokemonResponse findPokemonByName(@PathVariable("pokemonName") String pokemonName);

        @GetMapping(value = "/{pokemonId}")
        PokemonResponse findPokemonById(@PathVariable("pokemonId") Integer id);
    }
}
