package mindchess.model.moveDelegates;

import mindchess.model.Board;
import mindchess.model.IBoard;
import mindchess.model.MovementLogicUtil;
import mindchess.model.Square;

import java.util.ArrayList;
import java.util.List;

public class RookMoveDelegate implements IMoveDelegate {

    @Override
    public List<Square> fetchMoves(IBoard board, Square squareToCheck, boolean pieceOnSquareHasMoved, boolean checkKingSuicide) {
        var legalSquares = new ArrayList<Square>();

        legalSquares.addAll(MovementLogicUtil.up(board, squareToCheck, 7));
        legalSquares.addAll(MovementLogicUtil.right(board, squareToCheck, 7));
        legalSquares.addAll(MovementLogicUtil.down(board, squareToCheck, 7));
        legalSquares.addAll(MovementLogicUtil.left(board, squareToCheck, 7));

        return legalSquares;
    }
}
