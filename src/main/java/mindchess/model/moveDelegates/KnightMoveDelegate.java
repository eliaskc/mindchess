package mindchess.model.moveDelegates;

import mindchess.model.Board;
import mindchess.model.MovementLogicUtil;
import mindchess.model.Square;

import java.util.ArrayList;
import java.util.List;

public class KnightMoveDelegate implements IMoveDelegate {

    @Override
    public List<Square> fetchMoves(Board board, Square squareToCheck, boolean pieceOnSquareHasMoved, boolean checkKingSuicide) {
        var legalSquares = new ArrayList<Square>();

        int x = squareToCheck.getX();
        int y = squareToCheck.getY();

        MovementLogicUtil.addSquareIfLegal(board, new Square(x + 1, y - 2), squareToCheck, legalSquares);
        MovementLogicUtil.addSquareIfLegal(board, new Square(x + 2, y - 1), squareToCheck, legalSquares);
        MovementLogicUtil.addSquareIfLegal(board, new Square(x + 2, y + 1), squareToCheck, legalSquares);
        MovementLogicUtil.addSquareIfLegal(board, new Square(x + 1, y + 2), squareToCheck, legalSquares);
        MovementLogicUtil.addSquareIfLegal(board, new Square(x - 1, y + 2), squareToCheck, legalSquares);
        MovementLogicUtil.addSquareIfLegal(board, new Square(x - 2, y + 1), squareToCheck, legalSquares);
        MovementLogicUtil.addSquareIfLegal(board, new Square(x - 2, y - 1), squareToCheck, legalSquares);
        MovementLogicUtil.addSquareIfLegal(board, new Square(x - 1, y - 2), squareToCheck, legalSquares);

        return legalSquares;
    }
}
