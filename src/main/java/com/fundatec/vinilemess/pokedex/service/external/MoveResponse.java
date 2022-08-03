package com.fundatec.vinilemess.pokedex.service.external;

public class MoveResponse {
    private MoveDetailsResponse move;

    public MoveResponse(){}

    public MoveResponse(String moveName) {
        this.move = new MoveDetailsResponse(moveName);
    }

    public MoveDetailsResponse getMove() {
        return move;
    }
}
