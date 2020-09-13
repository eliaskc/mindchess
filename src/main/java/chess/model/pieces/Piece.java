package chess.model.pieces;

import chess.model.Color;
import chess.model.Square;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Piece {
    Square position;
    boolean isActive;
    Color color;
    boolean mark = false;
    Image pieceImage = null; //not sure if this should be in the model or in the application

    /*public Piece(boolean isActive, Color color) {
        this.isActive = isActive;
        this.color = color;
    }*/

    public Piece(Square position, boolean isActive, Color color) {
        this.position = position;
        this.isActive = isActive;
        this.color = color;
    }

    public Square getPosition() {
        return position;
    }

    public void setPosition(Square position) {
        this.position = position;
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

    public Image getPieceImage() {
        return pieceImage;
    }

    public abstract void fetchImage();
}
