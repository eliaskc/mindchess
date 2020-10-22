package mindchess.model.moveDelegates;

import mindchess.model.Board;
import mindchess.model.MovementLogicUtil;
import mindchess.model.Square;

import java.util.ArrayList;
import java.util.List;

public class BishopMoveDelegate implements IMoveDelegate {

    @Override
    public List<Square> fetchMoves(Board board, Square squareToCheck, boolean pieceOnSquareHasMoved, boolean checkKingSuicide) {
        var legalSquares = new ArrayList<Square>();

        legalSquares.addAll(MovementLogicUtil.upLeft(board, squareToCheck, 7));
        legalSquares.addAll(MovementLogicUtil.upRight(board, squareToCheck, 7));
        legalSquares.addAll(MovementLogicUtil.downRight(board, squareToCheck, 7));
        legalSquares.addAll(MovementLogicUtil.downLeft(board, squareToCheck, 7));

        return legalSquares;
    }
}
