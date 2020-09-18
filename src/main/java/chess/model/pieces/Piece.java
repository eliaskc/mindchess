package chess.model.pieces;

import chess.model.Color;
import chess.model.Square;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import static chess.model.Color.BLACK;
import static chess.model.Color.WHITE;

/**
 * Piece represents a chess piece on the board
 *
 * Also fetches its own image
 */
public abstract class Piece {
    Square square;
    boolean isActive;
    Color color;
    String blackImageURL;
    String whiteImageURL;
    boolean mark = false;
    ImageView pieceImage = null; //not sure if this should be in the model or in the application

    public Piece(Square square, boolean isActive, Color color) {
        this.square = square;
        this.isActive = isActive;
        this.color = color;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    /*
    public void setPosition(Square position) {
        this.position = position;
    }
     */

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public ImageView getPieceImage() {
        return pieceImage;
    }

    /**
     * fetches the pieces image from the resource map
     */
    public void fetchImage() {
        if(color.equals(WHITE)){
            pieceImage = new ImageView();
            pieceImage.setImage(new Image(getClass().getResourceAsStream(whiteImageURL)));
        } else if(color.equals(BLACK)) {
            pieceImage = new ImageView();
            pieceImage.setImage(new Image(getClass().getResourceAsStream(blackImageURL)));
        }
    }
}
