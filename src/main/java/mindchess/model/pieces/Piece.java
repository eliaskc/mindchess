package mindchess.model.pieces;

import mindchess.model.enums.ChessColor;
import mindchess.model.enums.PieceType;
import mindchess.model.moveDelegates.IMoveDelegate;

/**
 * Representation of a piece on the Board.
 * <p>
 * When it is created, it has a color, type and move delegate (which is how it is allowed to move).
 *
 * @author Erik Wessman, Elias Carlson, Elias Hallberg, Arvid Holmqvist
 */
class Piece implements IPiece {
    private final ChessColor pieceColor;
    private final PieceType pieceType;
    private boolean hasMoved = false;
    private final IMoveDelegate moveDelegate;
    private final int pieceValue;

    Piece(ChessColor pieceColor, PieceType pieceType, IMoveDelegate moveDelegate, int pieceValue) {
        this.pieceColor = pieceColor;
        this.pieceType = pieceType;
        this.moveDelegate = moveDelegate;
        this.pieceValue = pieceValue;
    }

    @Override
    public boolean getHasMoved() {
        return hasMoved;
    }

    @Override
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    @Override
    public ChessColor getColor() {
        return pieceColor;
    }

    @Override
    public PieceType getPieceType() {
        return pieceType;
    }

    @Override
    public IMoveDelegate getMoveDelegate() {
        return moveDelegate;
    }

    @Override
    public int getPieceValue() {
        return pieceValue;
    }
}
