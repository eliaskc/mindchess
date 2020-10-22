package mindchess.model.pieces;

import mindchess.model.ChessColor;
import mindchess.model.PieceType;
import mindchess.model.moveDelegates.IMoveDelegate;

/**
 * Interface for Piece
 */
public interface IPiece {
    boolean getHasMoved();

    void setHasMoved(boolean hasMoved);

    ChessColor getColor();

    PieceType getPieceType();

    IMoveDelegate getMoveDelegate();
}
