package com.fundatec.vinilemess.pokedex.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document
public class Pokemon {
    @Id
    private String id;
    private Integer pokedexId;
    private String name;
    private Integer weight;
    private Integer height;
    private List<Move> moves;
    private List<Type> types;
    private boolean deleted = false;

    public Pokemon(String id, Integer pokedexId, String name, Integer weight, Integer height, List<Move> moves, List<Type> types) {
        this.id = id;
        this.pokedexId = pokedexId;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.moves = moves;
        this.types = types;
    }

    public String getId() {
        return id;
    }

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

    public void markDeleted() {
        this.deleted = true;
    }
}
