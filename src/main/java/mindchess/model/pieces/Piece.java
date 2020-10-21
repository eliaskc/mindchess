package mindchess.model.pieces;

import mindchess.model.ChessColor;
import mindchess.model.PieceType;
import mindchess.model.moveDelegates.IMoveDelegate;

public class Piece implements IPiece {
    private ChessColor pieceColor;
    private PieceType pieceType;
    private boolean hasMoved = false;
    private IMoveDelegate moveDelegate;

    public Piece(ChessColor pieceColor, PieceType pieceType, IMoveDelegate moveDelegate) {
        this.pieceColor = pieceColor;
        this.pieceType = pieceType;
        this.moveDelegate = moveDelegate;
    }

    @Override
    public void setHasMoved(boolean hasMoved) {
        this.hasMoved = hasMoved;
    }

    @Override
    public boolean getHasMoved() {
        return hasMoved;
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
