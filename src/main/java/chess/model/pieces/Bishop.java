package chess.model.pieces;

import chess.model.Color;
import chess.model.Square;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static chess.model.Color.*;

public class Bishop extends Piece {
    public Bishop(Square position, boolean isActive, Color color) {
        super(position, isActive, color);
    }

    @Override
    public void fetchImage() {
        if(color.equals(WHITE)){
            pieceImage = new ImageView();
            pieceImage.setImage(new Image(getClass().getResourceAsStream("/chessPieces/white_bishop.png")));
            pieceImage.setX(position.getCoordinatesX() * 75 + 359);
            pieceImage.setY(position.getCoordinatesY() * 75 + 79);
        } else if(color.equals(BLACK)) {
            pieceImage = new ImageView();
            pieceImage.setImage(new Image(getClass().getResourceAsStream("/chessPieces/black_bishop.png")));
            pieceImage.setX(position.getCoordinatesX() * 75 + 359);
            pieceImage.setY(position.getCoordinatesY() * 75 + 79);
        }
    }
}
