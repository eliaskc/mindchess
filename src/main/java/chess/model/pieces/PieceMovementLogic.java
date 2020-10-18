package chess.model.pieces;

import chess.model.Board;
import chess.model.ChessColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static chess.model.ChessColor.*;

/**
 * Is responsible for finding legal moves
 */
public class PieceMovementLogic {
    private Board board = Board.getInstance();
    private boolean checkingOpponentLegalPointsInProgress = false;
    private boolean castlingPossible = false;
    private boolean enPassantPossible = false;

    private static PieceMovementLogic instance = null;

    public static PieceMovementLogic getInstance() {
        if (instance == null) {
            instance = new PieceMovementLogic();
        }
        return instance;
    }

    private PieceMovementLogic() {}

    public void setBoard(Board board) {
        this.board = board;
    }

    public boolean isCastlingPossible() {
        return castlingPossible;
    }

    public boolean isEnPassantPossible() {
        return enPassantPossible;
    }

    public void setCastlingPossible(boolean castlingPossible) {
        this.castlingPossible = castlingPossible;
    }

    public void setEnPassantPossible(boolean enPassantPossible) {
        this.enPassantPossible = enPassantPossible;
    }

    void up(ChessColor pieceToMoveColor, Point selectedPoint, int iterations, List<Point> legalPoints) {
        for (int i = selectedPoint.y - 1; i >= 0 && iterations > 0; i--, iterations--) {
            Point p = new Point(selectedPoint.x, i);
            if (addPoint(p, pieceToMoveColor, legalPoints)) break;
        }
    }

    void down(ChessColor pieceToMoveColor, Point selectedPoint, int iterations, List<Point> legalPoints) {
        for (int i = selectedPoint.y + 1; i < 8 && iterations > 0; i++, iterations--) {
            Point p = new Point(selectedPoint.x, i);
            if (addPoint(p, pieceToMoveColor, legalPoints)) break;
        }
    }

    void left(ChessColor pieceToMoveColor, Point selectedPoint, int iterations, List<Point> legalPoints) {
        for (int i = selectedPoint.x - 1; i >= 0 && iterations > 0; i--, iterations--) {
            Point p = new Point(i, selectedPoint.y);
            if (addPoint(p, pieceToMoveColor, legalPoints)) break;
        }
    }

    void right(ChessColor pieceToMoveColor, Point selectedPoint, int iterations, List<Point> legalPoints) {
        for (int i = selectedPoint.x + 1; i < 8 && iterations > 0; i++, iterations--) {
            Point p = new Point(i, selectedPoint.y);
            if (addPoint(p, pieceToMoveColor, legalPoints)) break;
        }
    }

    void upLeft(ChessColor pieceToMoveColor, Point selectedPoint, int iterations, List<Point> legalPoints) {
        Point p = new Point(selectedPoint.x, selectedPoint.y);
        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            p.x--;
            p.y--;
            if (addPoint(p, pieceToMoveColor, legalPoints)) break;
        }
    }

    void upRight(ChessColor pieceToMoveColor, Point selectedPoint, int iterations, List<Point> legalPoints) {
        Point p = new Point(selectedPoint.x, selectedPoint.y);
        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            p.x++;
            p.y--;
            if (addPoint(p, pieceToMoveColor, legalPoints)) break;
        }
    }

    void downRight(ChessColor pieceToMoveColor, Point selectedPoint, int iterations, List<Point> legalPoints) {
        Point p = new Point(selectedPoint.x, selectedPoint.y);
        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            p.x++;
            p.y++;
            if (addPoint(p, pieceToMoveColor, legalPoints)) break;
        }
    }

    void downLeft(ChessColor pieceToMoveColor, Point selectedPoint, int iterations, List<Point> legalPoints) {
        Point p = new Point(selectedPoint.x, selectedPoint.y);

        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            p.x--;
            p.y++;
            if (addPoint(p, pieceToMoveColor, legalPoints)) break;
        }
    }

    /**
     * Adds point to the list of legal moves if the point is inside the board AND:
     * - the point is empty
     * - the point has a piece of the opposite color
     *
     * @param p           point to move to
     * @param pieceToMoveColor
     * @return returns boolean that breaks the loop where the method was called, if a point has been added
     */
    boolean addPoint(Point p, ChessColor pieceToMoveColor, List<Point> listToAddTo) {
        boolean breakLoop;      //Used just to be extra clear, instead of return false or true
        if (p.x >= 0 && p.x < 8 && p.y >= 0 && p.y < 8) {
            if (isUnoccupied(p)) {
                listToAddTo.add(new Point(p.x, p.y));
                breakLoop = false;
            } else if (!board.pieceOnPointColorEquals(p, pieceToMoveColor)) {
                listToAddTo.add(new Point(p.x, p.y));
                breakLoop = true;
            } else {
                breakLoop = true;
            }
        } else {
            breakLoop = true;
        }
        return breakLoop;
    }

    boolean isUnoccupied(Point p) {
        return !board.isOccupied(p);
    }

    boolean isOccupied(Point p) {
        return board.isOccupied(p);
    }

/*    private List<Point> fetchOpponentLegalPoints(ChessColor color){
        List<Point> opponentLegalPoints = new ArrayList<>();
        checkingOpponentLegalPointsInProgress = true;

        for (Map.Entry<Point, Piece> entry : boardMap.entrySet()) {
            if(!(entry.getValue().getColor().equals(color))){
                opponentLegalPoints.addAll(fetchLegalMoves(boardMap.get(entry.getKey()), entry.getKey()));
            }
        }

        checkingOpponentLegalPointsInProgress = false;
        return opponentLegalPoints;
    }*/

    Point fetchKingPoint(ChessColor color) {
        return board.fetchKingPoint(color);
    }

    boolean pieceOnPointColorEquals(Point p, ChessColor chessColor) {
        return board.pieceOnPointColorEquals(p, chessColor);
    }

    boolean isPieceOnPointRook(Point point) {
        return board.isPieceOnPointRook(point);
    }
}
