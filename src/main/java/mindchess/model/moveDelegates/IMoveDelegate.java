package mindchess.model.moveDelegates;

import mindchess.model.IBoard;
import mindchess.model.Square;

import java.util.List;

/**
 * Interface for MoveDelegate classes that return the legal moves for each type of piece
 *
 * @author Erik Wessman, Elias Carlson, Elias Hallberg, Arvid Holmqvist
 */
public interface IMoveDelegate {
    List<Square> fetchMoves(IBoard board, Square squareToCheck, boolean pieceOnSquareHasMoved, boolean checkKingSuicide);
}
