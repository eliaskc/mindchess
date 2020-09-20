package chess.model;

import java.util.List;

/**
 * Is responisble for finding legal moves
 */
public class Movement {
    List<Piece> pieces;
    Square[][] squares;

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public void setSquares(Square[][] squares) {
        this.squares = squares;
    }
}
