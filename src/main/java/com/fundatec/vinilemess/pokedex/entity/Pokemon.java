package com.fundatec.vinilemess.pokedex.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document
@Getter
@Builder
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

    public void markDeleted() {
        this.deleted = true;
    }
}
