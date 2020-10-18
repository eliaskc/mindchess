package chess.model.pieces;

import chess.model.ChessColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static chess.model.ChessColor.BLACK;
import static chess.model.ChessColor.WHITE;

public class Pawn implements IPiece {
    PieceMovementLogic pieceMovementLogic = new PieceMovementLogic();

    String pieceName = "Pawn";
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

        if (this.getColor() == WHITE) {
            if (pieceMovementLogic.isUnoccupied(new Point(x, y - 1))) {
                pieceMovementLogic.up(chessColor, selectedPoint, 1, legalPoints);

                if (pieceMovementLogic.isUnoccupied(new Point(x, y - 2)) && !hasMoved) {
                    pieceMovementLogic.addPoint(new Point(x, y - 2), chessColor, legalPoints);
                }
            }
            if (pieceMovementLogic.isOccupied(new Point(x + 1, y - 1))) pieceMovementLogic.upRight(chessColor, selectedPoint, 1, legalPoints);
            if (pieceMovementLogic.isOccupied(new Point(x - 1, y - 1))) pieceMovementLogic.upLeft(chessColor, selectedPoint, 1, legalPoints);
        } else if (this.getColor() == BLACK) {
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


    //TODO
    /*List<Point> getEnPassantPoints(Point selectedPoint) {
        List<Point> enPassantPoints = new ArrayList<>();
        if (plies.size() == 0) return enPassantPoints;

        Ply lastPly = plies.get(plies.size() - 1);
        Piece lastMovedPiece = lastPly.getMovedPiece();

        if (lastMovedPiece.getPieceType() == PAWN && lastMovedPiece.getColor() != this.getColor() && Math.abs(lastPly.getMovedFrom().y - lastPly.getMovedTo().y) == 2) {
            if ((lastPly.getMovedTo().x == selectedPoint.x + 1 || lastPly.getMovedTo().x == selectedPoint.x - 1) && lastPly.getMovedTo().y == selectedPoint.y) {
                if (lastMovedPiece.getColor() == BLACK) {
                    enPassantPoints.add(new Point(lastPly.getMovedTo().x, lastPly.getMovedTo().y - 1));
                } else if (lastMovedPiece.getColor() == WHITE) {
                    enPassantPoints.add(new Point(lastPly.getMovedTo().x, lastPly.getMovedTo().y + 1));
                }
            }
        }
        return enPassantPoints;
    }*/
}
