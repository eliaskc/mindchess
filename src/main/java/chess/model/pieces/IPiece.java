package chess.model.pieces;

import chess.model.ChessColor;
import chess.model.PieceType;
import chess.model.moveDelegates.IMoveDelegate;

public interface IPiece {
    void setHasMoved(boolean hasMoved);
    boolean getHasMoved();
    ChessColor getColor();
    PieceType getPieceType();
    IMoveDelegate getMoveDelegate();
}
