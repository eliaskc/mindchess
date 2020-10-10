package chess.model.GameState;

import chess.GameObserver;
import chess.model.*;
import chess.model.GameState.GameState;

import java.awt.*;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NoPieceSelectedState implements GameState {
    private Map<Point, Piece> boardMap;
    private List<Ply> plies;
    private Movement movement;
    private List<Point> legalPoints;
    private IGameStateChanger context;

    public NoPieceSelectedState(Map<Point, Piece> boardMap, List<Ply> plies,Movement movement,List<Point> legalPoints, IGameStateChanger context) {
        this.boardMap = boardMap;
        this.plies = plies;
        this.context = context;
        this.movement = movement;
        this.legalPoints = legalPoints;
    }

    @Override
    public void handleInput(int x, int y) {
        Point pointSelected = new Point(x,y);
        if(pointContainsPiece(pointSelected)) {
            fetchLegalMoves(pointSelected);
            if(legalPoints.size() == 0) return;
            notifyDrawLegalMoves();
            context.setGameState(new PieceSelectedState(pointSelected,boardMap,plies,movement,legalPoints,context));
            return;
        }

    }

    private void fetchLegalMoves(Point pointSelected) {
        legalPoints.addAll(movement.pieceMoveDelegation(boardMap.get(pointSelected), pointSelected));
    }


    private boolean pointContainsPiece(Point point){
        if(boardMap.containsKey(point)) return true;
        return false;
    }

    private void notifyDrawLegalMoves() {
        context.notifyDrawLegalMoves();
    }
}
