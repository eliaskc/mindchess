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

        return legalPoints;
    }
}
