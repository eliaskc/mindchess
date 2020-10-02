package chess.model;

/**
 * Piece represents a chess piece on the board
 *
 * Also fetches its own image
 */
public class Piece {
    private boolean isActive;
    private Color color;
    private PieceType pieceType;
    private boolean mark = false;

    public Piece(boolean isActive, Color color, PieceType pieceType) {
        this.isActive = isActive;
        this.color = color;
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

    public Color getColor() {
        return color;
    }
}
