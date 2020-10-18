package chess.model;

/**
 * Piece represents a chess piece on the board
 * <p>
 * Also fetches its own image
 */
public class Piece {
    private final ChessColor chessColor;
    private final PieceType pieceType;

    public Piece(ChessColor chessColor, PieceType pieceType) {
        this.chessColor = chessColor;
        this.pieceType = pieceType;
    }

    //public becouse of imagehandeler
    public PieceType getPieceType() {
        return pieceType;
    }

    public ChessColor getColor() {
        return chessColor;
    }
}
