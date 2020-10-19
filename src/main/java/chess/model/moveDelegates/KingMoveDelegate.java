package chess.model.moveDelegates;

import chess.model.Board;
import chess.model.util.MovementLogicUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class KingMoveDelegate implements IMoveDelegate {

    @Override
    public List<Point> fetchMoves(Board board, Point pointToCheck, boolean pieceOnPointHasMoved) {
        var legalPoints = new ArrayList<Point>();

        legalPoints.addAll(MovementLogicUtil.up(board, pointToCheck, 1));
        legalPoints.addAll(MovementLogicUtil.right(board, pointToCheck, 1));
        legalPoints.addAll(MovementLogicUtil.down(board, pointToCheck, 1));
        legalPoints.addAll(MovementLogicUtil.left(board, pointToCheck, 1));

        legalPoints.addAll(MovementLogicUtil.upLeft(board, pointToCheck, 1));
        legalPoints.addAll(MovementLogicUtil.upRight(board, pointToCheck, 1));
        legalPoints.addAll(MovementLogicUtil.downRight(board, pointToCheck, 1));
        legalPoints.addAll(MovementLogicUtil.downLeft(board, pointToCheck, 1));

        legalPoints.addAll(getCastlingPoints(board, pointToCheck, pieceOnPointHasMoved));

        return legalPoints;
    }

    List<Point> getCastlingPoints(Board board, Point pointToCheck, boolean hasMoved) {
        List<Point> castlingPoints = new ArrayList<>();

        if (!hasMoved) {
            if (checkRightCastling(board, pointToCheck, hasMoved)) {
                castlingPoints.add(new Point(pointToCheck.x + 2, pointToCheck.y, true));
            }
            if (checkLeftCastling(board, pointToCheck, hasMoved)) {
                castlingPoints.add(new Point(pointToCheck.x - 2, pointToCheck.y));
            }
        }
        return castlingPoints;
    }

    /**
     *
     * @param board
     * @param pointToCheck
     * @param hasMoved
     * @return
     */
    private boolean checkRightCastling(Board board, Point pointToCheck, boolean hasMoved) {
        for (int i = pointToCheck.x + 1; i <= pointToCheck.x + 2; i++) {
            if (MovementLogicUtil.isOccupied(board, new Point(i, pointToCheck.y))) {
                return false;
            }
        }
        Point p = new Point(pointToCheck.x + 3, pointToCheck.y);
        if (MovementLogicUtil.isOccupied(board, p)) {
            return board.isPieceOnPointRook(p) && !hasMoved && board.pieceOnPointColorEquals(p, board.fetchPieceOnPointColor(pointToCheck));
        }
        return false;
    }

    /**
     *
     * @param board
     * @param pointToCheck
     * @param hasMoved
     * @return
     */
    private boolean checkLeftCastling(Board board, Point pointToCheck, boolean hasMoved) {
        for (int i = pointToCheck.x - 1; i >= pointToCheck.x - 3; i--) {
            if (MovementLogicUtil.isOccupied(board, new Point(i, pointToCheck.y))) {
                return false;
            }
        }
        Point p = new Point(pointToCheck.x - 4, pointToCheck.y);
        if (MovementLogicUtil.isOccupied(board, p)) {
            return board.isPieceOnPointRook(p) && !hasMoved && board.pieceOnPointColorEquals(p, board.fetchPieceOnPointColor(pointToCheck));
        }
        return false;
    }
}
