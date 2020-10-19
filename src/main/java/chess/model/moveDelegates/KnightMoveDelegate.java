package chess.model.moveDelegates;

import chess.model.Board;
import chess.model.util.MovementLogicUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class KnightMoveDelegate implements IMoveDelegate {

    @Override
    public List<Point> fetchMoves(Board board, Point pointToCheck, boolean pieceOnPointHasMoved) {
        var legalPoints = new ArrayList<Point>();

        int x = pointToCheck.x;
        int y = pointToCheck.y;

        MovementLogicUtil.addPointIfLegal(board, new Point(x + 1, y - 2), pointToCheck, legalPoints);
        MovementLogicUtil.addPointIfLegal(board, new Point(x + 2, y - 1), pointToCheck, legalPoints);
        MovementLogicUtil.addPointIfLegal(board, new Point(x + 2, y + 1), pointToCheck, legalPoints);
        MovementLogicUtil.addPointIfLegal(board, new Point(x + 1, y + 2), pointToCheck, legalPoints);
        MovementLogicUtil.addPointIfLegal(board, new Point(x - 1, y + 2), pointToCheck, legalPoints);
        MovementLogicUtil.addPointIfLegal(board, new Point(x - 2, y + 1), pointToCheck, legalPoints);
        MovementLogicUtil.addPointIfLegal(board, new Point(x - 2, y - 1), pointToCheck, legalPoints);
        MovementLogicUtil.addPointIfLegal(board, new Point(x - 1, y - 2), pointToCheck, legalPoints);

        return legalPoints;
    }
}
