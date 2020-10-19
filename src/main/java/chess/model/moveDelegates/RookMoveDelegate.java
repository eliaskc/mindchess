package chess.model.moveDelegates;

import chess.model.Board;
import chess.model.util.MovementLogicUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RookMoveDelegate implements IMoveDelegate {

    @Override
    public List<Point> fetchMoves(Board board, Point pointToCheck, boolean pieceOnPointHasMoved) {
        var legalPoints = new ArrayList<Point>();

        legalPoints.addAll(MovementLogicUtil.up(board, pointToCheck, 7));
        legalPoints.addAll(MovementLogicUtil.right(board, pointToCheck, 7));
        legalPoints.addAll(MovementLogicUtil.down(board, pointToCheck, 7));
        legalPoints.addAll(MovementLogicUtil.left(board, pointToCheck, 7));

        return legalPoints;
    }
}
