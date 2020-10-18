package chess.model.pieces;

import chess.model.ChessColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static chess.model.ChessColor.BLACK;
import static chess.model.ChessColor.WHITE;

public class Pawn implements IPiece {
    private PieceMovementLogic pieceMovementLogic = PieceMovementLogic.getInstance();
    private String pieceName = "Pawn";
    private ChessColor chessColor;
    private boolean hasMoved = false;

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

    public Pawn(ChessColor chessColor) {
        this.chessColor = chessColor;
    }

    @Override
    public List<Point> fetchLegalMoves(Point selectedPoint) {
        List<Point> legalPoints = new ArrayList<>();

        findLegalMoves(legalPoints, selectedPoint);

        return legalPoints;
    }

    public void findLegalMoves(List<Point> legalPoints, Point selectedPoint) {
        int x = selectedPoint.x;
        int y = selectedPoint.y;

        if (chessColor == WHITE) {
            if (pieceMovementLogic.isUnoccupied(new Point(x, y - 1))) {
                pieceMovementLogic.up(chessColor, selectedPoint, 1, legalPoints);

                if (pieceMovementLogic.isUnoccupied(new Point(x, y - 2)) && !hasMoved) {
                    pieceMovementLogic.addPoint(new Point(x, y - 2), chessColor, legalPoints);
                }
            }
            if (pieceMovementLogic.isOccupied(new Point(x + 1, y - 1))) pieceMovementLogic.upRight(chessColor, selectedPoint, 1, legalPoints);
            if (pieceMovementLogic.isOccupied(new Point(x - 1, y - 1))) pieceMovementLogic.upLeft(chessColor, selectedPoint, 1, legalPoints);
        } else if (chessColor == BLACK) {
            if (pieceMovementLogic.isUnoccupied(new Point(x, y + 1))) {
                pieceMovementLogic.down(chessColor, selectedPoint, 1, legalPoints);

                if (pieceMovementLogic.isUnoccupied(new Point(x, y + 2)) && !hasMoved) {
                    pieceMovementLogic.addPoint(new Point(x, y + 2), chessColor, legalPoints);
                }
            }
            if (pieceMovementLogic.isOccupied(new Point(x + 1, y + 1))) pieceMovementLogic.downRight(chessColor, selectedPoint, 1, legalPoints);
            if (pieceMovementLogic.isOccupied(new Point(x - 1, y + 1))) pieceMovementLogic.downLeft(chessColor, selectedPoint, 1, legalPoints);
        }

//        legalPoints.addAll(getEnPassantPoints(selectedPoint));
    }
}
