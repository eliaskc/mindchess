package mindchess.model.pieces;

import mindchess.model.enums.ChessColor;
import mindchess.model.enums.PieceType;
import mindchess.model.moveDelegates.IMoveDelegate;

/**
 * Interface for Piece
 *
 * @author Erik Wessman, Elias Carlson, Elias Hallberg, Arvid Holmqvist
 */
public interface IPiece {
    boolean getHasMoved();

    void setHasMoved(boolean hasMoved);

    ChessColor getColor();

    PieceType getPieceType();

    IMoveDelegate getMoveDelegate();

    int getPieceValue();
}
