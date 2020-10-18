package chess.model.pieces;

import chess.model.ChessColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Queen implements IPiece {
    PieceMovementLogic pieceMovementLogic = new PieceMovementLogic();
    String pieceName = "Queen";
    ChessColor chessColor;
    boolean hasMoved = false;

    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    @Override
    public ChessColor getColor() {
        return chessColor;
    }

    @Override
    public String getPieceName() {
        return pieceName;
    }

    public Queen(ChessColor chessColor) {
        this.chessColor = chessColor;
    }

    @Override
    public List<Point> fetchLegalMoves(Point selectedPoint) {
        List<Point> legalPoints = new ArrayList<>();

        findLegalMoves(legalPoints, selectedPoint);

        return legalPoints;
    }

    public void findLegalMoves(List<Point> legalPoints, Point selectedPoint) {
        pieceMovementLogic.up(chessColor, selectedPoint, 7, legalPoints);
        pieceMovementLogic.right(chessColor, selectedPoint, 7, legalPoints);
        pieceMovementLogic.down(chessColor, selectedPoint, 7, legalPoints);
        pieceMovementLogic.left(chessColor, selectedPoint, 7, legalPoints);

        pieceMovementLogic.upLeft(chessColor, selectedPoint, 7, legalPoints);
        pieceMovementLogic.upRight(chessColor, selectedPoint, 7, legalPoints);
        pieceMovementLogic.downRight(chessColor, selectedPoint, 7, legalPoints);
        pieceMovementLogic.downLeft(chessColor, selectedPoint, 7, legalPoints);
    }
}
