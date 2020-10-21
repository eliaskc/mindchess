package mindchess.model.pieces;

import mindchess.model.ChessColor;
import mindchess.model.PieceType;
import mindchess.model.moveDelegates.IMoveDelegate;

/**
 * Interface for Piece
 */
public interface IPiece {
    void setHasMoved(boolean hasMoved);
    boolean getHasMoved();
    ChessColor getColor();
    PieceType getPieceType();
    IMoveDelegate getMoveDelegate();
}
