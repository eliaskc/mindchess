package mindchess.model.moveDelegates;

import mindchess.model.Board;
import mindchess.model.Square;

import java.util.List;

public interface IMoveDelegate {
    List<Square> fetchMoves(Board board, Square squareToCheck, boolean pieceOnSquareHasMoved, boolean checkKingSuicide);
}
