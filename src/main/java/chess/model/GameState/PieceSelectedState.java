package chess.model.GameState;

import chess.model.GameState.GameState;
import chess.model.IGameStateChanger;
import chess.model.Piece;
import chess.model.Ply;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PieceSelectedState implements GameState {

    Point markedPoint;
    Map<Point, Piece> boardMap;
    List<Ply> plies;
    IGameStateChanger context;
    public PieceSelectedState(Point markedPoint) {
        this.markedPoint = markedPoint;
    }

    @Override
    public void handleInput(int x, int y) {

    }
}
