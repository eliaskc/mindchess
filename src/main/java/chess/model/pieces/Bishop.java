package chess.model.pieces;

import chess.model.Color;
import chess.model.Square;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static chess.model.Color.*;

public class Bishop extends Piece {
    public Bishop(Square position, boolean isActive, Color color) {
        super(position, isActive, color);
        whiteImageURL = "/chessPieces/white_bishop.png";
        blackImageURL = "/chessPieces/black_bishop.png";
    }
}
