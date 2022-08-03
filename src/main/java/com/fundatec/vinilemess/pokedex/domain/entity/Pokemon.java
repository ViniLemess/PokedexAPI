package com.fundatec.vinilemess.pokedex.domain.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Document
public class Pokemon {

    @Id
    private String id;

    @Positive
    @Min(value = 906)
    private Integer pokedexId;

    @NotBlank
    private String name;

    @NotNull
    @Positive
    private Integer weight;

    @NotNull
    @Positive
    private Integer height;

    @NotEmpty
    private List<Move> moves;

    @NotEmpty
    @Size(max = 2)
    private List<Type> types;

    private boolean deleted = false;

    public Pokemon(Integer pokedexId, String name, Integer weight, Integer height, List<Move> moves, List<Type> types) {
        this.pokedexId = pokedexId;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.moves = moves;
        this.types = types;
    }

    public Pokemon() {}

    public Integer getPokedexId() {
        return pokedexId;
    }

    public String getName() {
        return name;
    }

    public Integer getWeight() {
        return weight;
    }

    public Integer getHeight() {
        return height;
    }

    public List<Move> getMoves() {
        return moves;
    }

    public List<Type> getTypes() {
        return types;
    }

    public boolean isDeleted() {
        return deleted;
    }
}
