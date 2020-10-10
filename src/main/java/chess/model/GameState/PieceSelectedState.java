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

import static chess.model.ChessColor.BLACK;
import static chess.model.ChessColor.WHITE;

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

    private void checkMove(Point clickedPoint) {
        if (legalPoints.contains(clickedPoint)) {
            plies.add(new Ply(markedPoint, clickedPoint, boardMap.get(markedPoint), currentPlayer));
            makeSpecialMoves(markedPoint, clickedPoint);
            move(markedPoint, clickedPoint);
            if (!checkPawnPromotion(clickedPoint)) {
                switchPlayer();
            }
            winConditionCheck();
            notifyDrawPieces();
        }
        legalPoints.clear();
        markedPoint = null;
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
        if (boardMap.get(moveTo) != null) {
            takePiece(moveTo);
        }

        boardMap.put(moveTo, boardMap.get(moveFrom));
        boardMap.remove(moveFrom);
    }

    private void takePiece(Point pointToTake) {
        deadPieces.add(boardMap.remove(pointToTake));
        notifyDrawDeadPieces();
    }

    private void notifyDrawPieces(){
        context.notifyDrawPieces();
    }

    private void notifyDrawDeadPieces(){
        context.notifyDrawDeadPieces();
    }

}
