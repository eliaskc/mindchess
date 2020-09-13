package chess.model.pieces;

import chess.model.Color;
import chess.model.Square;

public class Queen extends Piece {
    public Queen(Square position, boolean isActive, Color color) {
        super(position, isActive, color);
        whiteImageURL = "/chessPieces/white_queen.png";
        blackImageURL = "/chessPieces/black_queen.png";
    }
}
