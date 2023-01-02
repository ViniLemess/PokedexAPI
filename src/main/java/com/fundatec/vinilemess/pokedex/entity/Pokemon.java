package com.fundatec.vinilemess.pokedex.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document
@Getter
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

    @Builder
    public Pokemon(String id, Integer pokedexId, String name, Integer weight, Integer height, List<Move> moves, List<Type> types) {
        this.id = id;
        this.pokedexId = pokedexId;
        this.name = name;
        this.weight = weight;
        this.height = height;
        this.moves = moves;
        this.types = types;
    }

    public void markDeleted() {
        this.deleted = true;
    }

    public void restorePokemon() {
        this.deleted = false;
    }
}
