package chess.model.GameState;

import chess.model.*;
import chess.model.GameState.GameState;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static chess.model.ChessColor.BLACK;
import static chess.model.ChessColor.WHITE;
import static chess.model.PieceType.PAWN;

public class PieceSelectedState implements GameState {

    private Point markedPoint;
    private Map<Point, Piece> boardMap;
    private List<Ply> plies;
    private Movement movement;
    private List<Point> legalPoints;
    private IGameStateChanger context;
    private Player currentPlayer;
    private List<Piece> deadPieces;

    public PieceSelectedState(Map<Point, Piece> boardMap, List<Ply> plies, Movement movement, List<Point> legalPoints,List<Piece> deadPieces, Player currentPlayer ,IGameStateChanger context) {
        this.boardMap = boardMap;
        this.plies = plies;
        this.movement = movement;
        this.legalPoints = legalPoints;
        this.context = context;
        this.deadPieces = deadPieces;
        this.currentPlayer = currentPlayer;
    }

    @Override
    public void handleInput(int x, int y) {
        checkMove(x,y);
    }

    private void checkMove(int x, int y) {
        Point selectedPoint = new Point(x,y);
        if (legalPoints.contains(selectedPoint)) {
            plies.add(new Ply(markedPoint, selectedPoint, boardMap.get(markedPoint), currentPlayer));
            makeSpecialMoves(markedPoint, selectedPoint);
            move(markedPoint, selectedPoint);
            notifyDrawPieces();
            switchPlayer();
        }
        context.setGameState(IGameStateChanger.GameStates.NoPieceSelected);
        legalPoints.clear();
        notifyDrawLegalMoves();
    }

    private void makeSpecialMoves(Point markedPoint, Point clickedPoint) {
        //castling
        if (movement.getCastlingPoints().size() != 0 && movement.getCastlingPoints().contains(clickedPoint)) {
            if (clickedPoint.x > markedPoint.x) {
                move(new Point(clickedPoint.x + 1, clickedPoint.y), new Point(clickedPoint.x - 1, clickedPoint.y));
            } else if (clickedPoint.x < markedPoint.x) {
                move(new Point(clickedPoint.x - 2, clickedPoint.y), new Point(clickedPoint.x + 1, clickedPoint.y));
            }
        }

        //en passant
        if (movement.getEnPassantPoints().size() != 0 && movement.getEnPassantPoints().contains(clickedPoint)) {
            if (boardMap.get(markedPoint).getColor() == WHITE) {
                takePiece(new Point(clickedPoint.x, clickedPoint.y + 1));
            } else if (boardMap.get(markedPoint).getColor() == BLACK) {
                takePiece(new Point(clickedPoint.x, clickedPoint.y - 1));
            }
        }
    }

    private void move(Point moveFrom, Point moveTo) {
        if (boardMap.containsKey(moveTo)) {
            takePiece(moveTo);
        }

        boardMap.put(moveTo, boardMap.get(moveFrom));
        boardMap.remove(moveFrom);
    }

    private void takePiece(Point pointToTake) {
        deadPieces.add(boardMap.remove(pointToTake));
        notifyDrawDeadPieces();
    }

    public void switchPlayer(){
        currentPlayer = currentPlayer.getOpponent();
        notifySwitchedPlayer();
    }


    private void notifyDrawPieces(){
        context.notifyDrawPieces();
    }

    private void notifyDrawDeadPieces(){
        context.notifyDrawDeadPieces();
    }

    private void notifySwitchedPlayer(){
        context.notifySwitchedPlayer();
    }

    private void notifyDrawLegalMoves(){
        context.notifyDrawLegalMoves();
    }

    @Override
    public void setMarkedPoint(Point markedPoint) {
        this.markedPoint = markedPoint;
    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public String getWinnerName() {
        return "null";
    }
}
