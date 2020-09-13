package chess.model.pieces;

import chess.model.Color;
import chess.model.Square;

public class King extends Piece {
    public King(Square position, boolean isActive, Color color) {
        super(position, isActive, color);
        whiteImageURL = "/chessPieces/white_king.png";
        blackImageURL = "/chessPieces/black_king.png";
    }
}
