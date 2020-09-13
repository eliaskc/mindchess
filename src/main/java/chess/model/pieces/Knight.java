package chess.model.pieces;

import chess.model.Color;
import chess.model.Square;

public class Knight extends Piece {
    public Knight(Square position, boolean isActive, Color color) {
        super(position, isActive, color);
        whiteImageURL = "/chessPieces/white_knight.png";
        blackImageURL = "/chessPieces/black_knight.png";
    }
}
