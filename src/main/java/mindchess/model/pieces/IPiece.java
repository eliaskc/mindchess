package mindchess.model.pieces;

import mindchess.model.enums.ChessColor;
import mindchess.model.enums.PieceType;
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

    int getPieceValue();
}
