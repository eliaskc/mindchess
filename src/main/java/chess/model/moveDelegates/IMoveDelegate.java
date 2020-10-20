package chess.model.moveDelegates;

import chess.model.Board;
import chess.model.Square;

import java.awt.*;
import java.util.List;

public interface IMoveDelegate {
    List<Square> fetchMoves(Board board, Square squareToCheck, boolean pieceOnSquareHasMoved, boolean checkKingSuicide);
}
