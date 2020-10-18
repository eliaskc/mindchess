package chess.model.pieces;

import chess.model.ChessColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Knight implements IPiece {
    PieceMovementLogic pieceMovementLogic = new PieceMovementLogic();
    String pieceName = "Knight";
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

    public Knight(ChessColor chessColor) {
        this.chessColor = chessColor;
    }

    @Override
    public List<Point> fetchLegalMoves(Point selectedPoint) {
        List<Point> legalPoints = new ArrayList<>();

        findLegalMoves(legalPoints, selectedPoint);

        return legalPoints;
    }

    private void findLegalMoves(List<Point> legalPoints, Point selectedPoint) {
        int x = selectedPoint.x;
        int y = selectedPoint.y;

        pieceMovementLogic.addPoint(new Point(x + 1, y - 2), chessColor, legalPoints);
        pieceMovementLogic.addPoint(new Point(x + 2, y - 1), chessColor, legalPoints);
        pieceMovementLogic.addPoint(new Point(x + 2, y + 1), chessColor, legalPoints);
        pieceMovementLogic.addPoint(new Point(x + 1, y + 2), chessColor, legalPoints);
        pieceMovementLogic.addPoint(new Point(x - 1, y + 2), chessColor, legalPoints);
        pieceMovementLogic.addPoint(new Point(x - 2, y + 1), chessColor, legalPoints);
        pieceMovementLogic.addPoint(new Point(x - 2, y - 1), chessColor, legalPoints);
        pieceMovementLogic.addPoint(new Point(x - 1, y - 2), chessColor, legalPoints);
    }
}
