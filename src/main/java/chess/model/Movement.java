package chess.model;

import java.util.List;

/**
 * Is responisble for finding legal moves
 */
public class Movement {
    private List<Piece> pieces;
    private Square[][] squares;

    //possible alternative to writing own getters and setters
//    private String _name;
//    public string Name; {get -> {thing++; return _name;}; set -> _name = value;};

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public void setSquares(Square[][] squares) {
        this.squares = squares;
    }
}
