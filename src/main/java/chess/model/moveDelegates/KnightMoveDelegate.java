package chess.model.moveDelegates;

import chess.model.Board;
import chess.model.Square;
import chess.model.MovementLogicUtil;

import java.util.ArrayList;
import java.util.List;

public class KnightMoveDelegate implements IMoveDelegate {

    @Override
    public List<Square> fetchMoves(Board board, Square squareToCheck, boolean pieceOnSquareHasMoved, boolean checkIfKingInCheck) {
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
