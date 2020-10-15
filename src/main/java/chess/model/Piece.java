package chess.model;

/**
 * Piece represents a chess piece on the board
 * <p>
 * Also fetches its own image
 */
public class Piece {
    private final ChessColor chessColor;
    private PieceType pieceType;

    public Piece(ChessColor chessColor, PieceType pieceType) {
        this.chessColor = chessColor;
        this.pieceType = pieceType;
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
