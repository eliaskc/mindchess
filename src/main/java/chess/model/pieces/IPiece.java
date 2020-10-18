package chess.model.pieces;

import chess.model.ChessColor;

import java.awt.*;
import java.util.List;

public interface IPiece {
    List<Point> fetchLegalMoves(Point selectedPoint);
    void setHasMoved(boolean hasMoved);
    ChessColor getColor();
    String getPieceName();
}
