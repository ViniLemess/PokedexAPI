package com.fundatec.vinilemess.pokedex.service.external;

public class TypeResponse {
    private Integer slot;
    private TypeDetailsResponse type;

    public TypeResponse(){}

    public TypeResponse(Integer slot, String typeName) {
        this.slot = slot;
        this.type = new TypeDetailsResponse(typeName);
    }

    public Integer getSlot() {
        return slot;
    }

    public TypeDetailsResponse getType() {
        return type;
    }
}
