package chess.model.moveDelegates;

import chess.model.Board;
import chess.model.Square;
import chess.model.util.MovementLogicUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RookMoveDelegate implements IMoveDelegate {

    @Override
    public List<Square> fetchMoves(Board board, Square squareToCheck, boolean pieceOnSquareHasMoved) {
        var legalSquares = new ArrayList<Square>();

        legalSquares.addAll(MovementLogicUtil.up(board, squareToCheck, 7));
        legalSquares.addAll(MovementLogicUtil.right(board, squareToCheck, 7));
        legalSquares.addAll(MovementLogicUtil.down(board, squareToCheck, 7));
        legalSquares.addAll(MovementLogicUtil.left(board, squareToCheck, 7));

        return legalSquares;
    }
}
