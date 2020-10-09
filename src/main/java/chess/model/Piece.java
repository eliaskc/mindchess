package chess.model;

/**
 * Piece represents a chess piece on the board
 * <p>
 * Also fetches its own image
 */
public class Piece {
    private boolean isActive;
    private final ChessColor chessColor;
    private PieceType pieceType;
    private final boolean mark = false;

    public Piece(boolean isActive, ChessColor chessColor, PieceType pieceType) {
        this.isActive = isActive;
        this.chessColor = chessColor;
        this.pieceType = pieceType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public ChessColor getColor() {
        return chessColor;
    }
}
