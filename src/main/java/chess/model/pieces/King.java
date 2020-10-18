package chess.model.pieces;

import chess.model.ChessColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class King implements IPiece {
    private PieceMovementLogic pieceMovementLogic = PieceMovementLogic.getInstance();
    private String pieceName = "King";
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

    public King(ChessColor chessColor) {
        this.chessColor = chessColor;
    }

    @Override
    public List<Point> fetchLegalMoves(Point selectedPoint) {
        List<Point> legalPoints = new ArrayList<>();

        findLegalMoves(legalPoints, selectedPoint);

        return legalPoints;
    }
    
    public void findLegalMoves(List<Point> legalPoints, Point selectedPoint) {
        pieceMovementLogic.up(chessColor, selectedPoint, 1, legalPoints);
        pieceMovementLogic.right(chessColor, selectedPoint, 1, legalPoints);
        pieceMovementLogic.down(chessColor, selectedPoint, 1, legalPoints);
        pieceMovementLogic.left(chessColor, selectedPoint, 1, legalPoints);

        pieceMovementLogic.upLeft(chessColor, selectedPoint, 1, legalPoints);
        pieceMovementLogic.upRight(chessColor, selectedPoint, 1, legalPoints);
        pieceMovementLogic.downRight(chessColor, selectedPoint, 1, legalPoints);
        pieceMovementLogic.downLeft(chessColor, selectedPoint, 1, legalPoints);

        List<Point> castlingPoints = getCastlingPoints(selectedPoint);
        if (castlingPoints.size() > 0){
            pieceMovementLogic.setCastlingPossible(true);
            legalPoints.addAll(castlingPoints);
        }

        /*if (!checkingOpponentLegalPointsInProgress) {
            List<Point> opponentLegalPoints = fetchOpponentLegalPoints(this.getColor());
            legalPoints.removeIf(p -> opponentLegalPoints.contains(p));
        }*/
    }

    List<Point> getCastlingPoints(Point selectedPoint) {
        List<Point> castlingPoints = new ArrayList<>();
        if (!hasMoved) {
            if (checkRightCastling(selectedPoint)) {
                castlingPoints.add(new Point(selectedPoint.x + 2, selectedPoint.y));
            }
            if (checkLeftCastling(selectedPoint)) {
                castlingPoints.add(new Point(selectedPoint.x - 2, selectedPoint.y));
            }
        }
        return castlingPoints;
    }

    /**
     * Checks that the conditions for castling to the right are filled
     *
     * @param selectedPoint
     * @return
     */
    private boolean checkRightCastling(Point selectedPoint) {
        for (int i = selectedPoint.x + 1; i <= selectedPoint.x + 2; i++) {
            if (pieceMovementLogic.isOccupied(new Point(i, selectedPoint.y))) {
                return false;
            }
        }

        Point p = new Point(selectedPoint.x + 3, selectedPoint.y);
        if (pieceMovementLogic.isOccupied(p)) {
            return pieceMovementLogic.isPieceOnPointRook(p) && !hasMoved && pieceMovementLogic.pieceOnPointColorEquals(p,chessColor);
        }
        return false;
    }

    /**
     * Checks that the conditions for castling to the left are filled
     *
     * @param selectedPoint
     * @return
     */
    private boolean checkLeftCastling(Point selectedPoint) {
        for (int i = selectedPoint.x - 1; i >= selectedPoint.x - 3; i--) {
            if (pieceMovementLogic.isOccupied(new Point(i, selectedPoint.y))) {
                return false;
            }
        }

        Point p = new Point(selectedPoint.x - 4, selectedPoint.y);
        if (pieceMovementLogic.isOccupied(p)) {
            return pieceMovementLogic.isPieceOnPointRook(p) && !hasMoved && pieceMovementLogic.pieceOnPointColorEquals(p,chessColor);
        }
        return false;
    }

    /*boolean isKingInCheck(Point kingPoint) {
        return fetchOpponentLegalPoints(boardMap.get(kingPoint).getColor()).contains(kingPoint);
    }*/
}
