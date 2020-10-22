package mindchess.model.pieces;

import mindchess.model.ChessColor;
import mindchess.model.PieceType;
import mindchess.model.moveDelegates.IMoveDelegate;

/**
 * Representation of a piece on the Board.
 * <p>
 * When it is created, it has a color, type and movedelegate(which is how it is allowed to move).
 */
public class Piece implements IPiece {
    private final ChessColor pieceColor;
    private final PieceType pieceType;
    private boolean hasMoved = false;
    private final IMoveDelegate moveDelegate;

    public Piece(ChessColor pieceColor, PieceType pieceType, IMoveDelegate moveDelegate) {
        this.pieceColor = pieceColor;
        this.pieceType = pieceType;
        this.moveDelegate = moveDelegate;
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
}
