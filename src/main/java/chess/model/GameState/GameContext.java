package chess.model.GameState;

import chess.model.Piece;
import chess.model.Ply;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameContext {
    private GameState gameState;

    public GameContext() {
        this.gameState = new NoPieceSelectedState();
    }
    void setStateNoPieceSelected(GameState newGameState){
        this.gameState = newGameState;
    }

    public void handleInput(int x, int y, Map<Point, Piece> boardMap, List<Ply> plies){
        gameState.handleInput(x,y,boardMap,plies,this);
    }
}
