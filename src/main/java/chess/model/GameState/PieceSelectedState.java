package chess.model.GameState;

import chess.model.GameState.GameState;
import chess.model.IGameStateChanger;
import chess.model.Movement;
import chess.model.Piece;
import chess.model.Ply;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PieceSelectedState implements GameState {

    Point markedPoint;
    private Map<Point, Piece> boardMap;
    private List<Ply> plies;
    private Movement movement;
    private List<Point> legalPoints;
    private IGameStateChanger context;

    public PieceSelectedState(Point markedPoint, Map<Point, Piece> boardMap, List<Ply> plies, Movement movement, List<Point> legalPoints, IGameStateChanger context) {
        this.markedPoint = markedPoint;
        this.boardMap = boardMap;
        this.plies = plies;
        this.movement = movement;
        this.legalPoints = legalPoints;
        this.context = context;
    }

    @Override
    public void handleInput(int x, int y) {

    }

}
