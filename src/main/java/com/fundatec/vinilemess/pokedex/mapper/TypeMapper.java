package com.fundatec.vinilemess.pokedex.mapper;

import com.fundatec.vinilemess.pokedex.dto.request.TypeRequest;
import com.fundatec.vinilemess.pokedex.dto.response.TypeResponse;
import com.fundatec.vinilemess.pokedex.entity.Type;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class TypeMapper {

    public static List<Type> requestsToEntities(List<TypeRequest> requests) {
        return requests.stream()
                .map(TypeMapper::requestToEntity)
                .toList();
    }

    public static List<TypeResponse> entitiesToResponses(List<Type> types) {
        return types.stream()
                .map(TypeMapper::entityToResponse)
                .toList();
    }
    private static Type requestToEntity(TypeRequest request) {
        return new Type(request.getSlot(), request.getName());
    }

    private static TypeResponse entityToResponse(Type type) {
        return new TypeResponse(type.getSlot(), type.getName());
    }
}
