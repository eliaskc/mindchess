package mindchess.model.moveDelegates;

import mindchess.model.IBoard;
import mindchess.model.Square;

import java.util.List;

public interface IMoveDelegate {
    List<Square> fetchMoves(IBoard board, Square squareToCheck, boolean pieceOnSquareHasMoved, boolean checkKingSuicide);
}
