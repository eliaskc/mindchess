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
    private Movement movement = new Movement();
    private List<Point> legalPoints = new ArrayList<>();
    private Point pointSelected;

    @Override
    public void handleInput(int x, int y, Map<Point, Piece> boardMap, List<Ply> plies, IGameStateChanger context) {
        this.boardMap = boardMap;
        this.plies = plies;
        pointSelected = new Point(x,y);
        if(pointContainsPiece(pointSelected)) {
            fetchLegalMoves();
            if(legalPoints.size() == 0) return;
            context.setGameState(new PieceSelectedState(pointSelected));
            return;
        }

    }

    private void fetchLegalMoves() {
        legalPoints.addAll(movement.pieceMoveDelegation(boardMap.get(pointSelected), pointSelected));
    }


    private boolean pointContainsPiece(Point point){
        if(boardMap.containsKey(point)) return true;
        return false;
    }

    private void notifyDrawLegalMoves() {
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.drawLegalMoves();
        }
    }
}
