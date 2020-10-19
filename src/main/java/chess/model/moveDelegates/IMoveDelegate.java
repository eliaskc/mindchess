package chess.model.moveDelegates;

import chess.model.Board;

import java.awt.*;
import java.util.List;

public interface IMoveDelegate {
    List<Point> fetchMoves(Board board, Point pointToCheck, boolean pieceOnPointHasMoved);
}
