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
    private Player currentPlayer;
    private List<Piece> deadPieces;
    private IGameStateChanger.GameStates gameState = IGameStateChanger.GameStates.NoPieceSelected;
    private Point markedPoint;


    public NoPieceSelectedState(Map<Point, Piece> boardMap, List<Ply> plies,Movement movement,List<Point> legalPoints,List<Piece> deadPieces,Player currentPlayer, IGameStateChanger context) {
        this.boardMap = boardMap;
        this.plies = plies;
        this.context = context;
        this.movement = movement;
        this.currentPlayer = currentPlayer;
        this.legalPoints = legalPoints;
        this.deadPieces = deadPieces;
        

    }

    @Override
    public void handleInput(int x, int y) {
        markedPoint = new Point(x,y);
        if(pointContainsPiece(markedPoint) && isPieceMyColor(markedPoint)) {
            fetchLegalMoves(markedPoint);
            if(legalPoints.size() == 0) return;
            notifyDrawLegalMoves();
            context.setGameState(IGameStateChanger.GameStates.PieceSelected);
            context.getGameState().setMarkedPoint(new Point(x,y));
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

    private boolean isPieceMyColor(Point point){
        return boardMap.get(point).getColor() == currentPlayer.getColor();
    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public String getWinnerName() {
        return "null";
    }

    @Override
    public void setMarkedPoint(Point markedPoint) {
        this.markedPoint = markedPoint;
    }
}
