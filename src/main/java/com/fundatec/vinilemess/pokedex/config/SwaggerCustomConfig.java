package com.fundatec.vinilemess.pokedex.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerCustomConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("SpringBoot RESTFULL Pokemon Api")
                        .description("Spring Boot facade to PokeAPI(https://pokeapi.co/) with support to creation of fakemons")
                        .version("2.0.0"))
                .components(new Components());
    }
}
