package chess.model.pieces;

import chess.model.ChessColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Rook implements IPiece {
    private PieceMovementLogic pieceMovementLogic = PieceMovementLogic.getInstance();
    private String pieceName = "Rook";
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

    public Rook(ChessColor chessColor) {
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
    }
}
