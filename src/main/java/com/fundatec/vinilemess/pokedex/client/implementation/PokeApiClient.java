package com.fundatec.vinilemess.pokedex.client;

import com.fundatec.vinilemess.pokedex.rest.handler.ExceptionHandlerAdvice;
import com.fundatec.vinilemess.pokedex.service.IPokemonIntegrationService;
import com.fundatec.vinilemess.pokedex.dto.response.PokemonResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PokeApiClient implements IPokemonIntegrationService {

    @Value("${poke-api-uri}")
    private String pokeApiUri;
    private final RestTemplate restTemplate;

    public PokeApiClient(RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder
                .errorHandler(new ExceptionHandlerAdvice())
                .build();
    }

    public PokemonResponse getPokemonResponseById(Integer id) {
        String uri = generateURLIntegration(id);
        return restTemplate.getForObject(uri, PokemonResponse.class);
    }

    public PokemonResponse getPokemonResponseByName(String name) {
        String uri = generateURLIntegration(name);
        return restTemplate.getForObject(uri, PokemonResponse.class);
    }

    public boolean validatePokemonExistenceByName(String name) {
        String uri = generateURLIntegration(name);
        try {
            restTemplate.getForObject(uri, PokemonResponse.class);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    private String generateURLIntegration(Integer id) {
        return this.pokeApiUri + "/" + id;
    }
    private String generateURLIntegration(String name) {
        return this.pokeApiUri + "/" + name;
    }
}
