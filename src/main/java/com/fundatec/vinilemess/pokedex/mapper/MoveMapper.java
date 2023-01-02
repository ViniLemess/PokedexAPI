package com.fundatec.vinilemess.pokedex.mapper;

import com.fundatec.vinilemess.pokedex.dto.request.MoveRequest;
import com.fundatec.vinilemess.pokedex.dto.response.MoveResponse;
import com.fundatec.vinilemess.pokedex.entity.Move;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MoveMapper {

    public static List<Move> requestsToEntities(List<MoveRequest> requests) {
        return requests.stream()
                .map(MoveMapper::requestToEntity)
                .toList();
    }

    public static List<MoveResponse> entitiesToResponses(List<Move> moves) {
        return moves.stream()
                .map(MoveMapper::entityToResponse)
                .toList();
    }
    private static Move requestToEntity(MoveRequest request) {
        return new Move(request.getName());
    }

    private static MoveResponse entityToResponse(Move move) {
        return new MoveResponse(move.getName());
    }
}
