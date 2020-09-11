package chess.model.pieces;

import chess.model.Color;
import chess.model.Square;

public abstract class Piece {
    Square position;
    boolean isActive;
    Color color;
    //Image image; //not sure if this should be in the model or in the application

    public Piece(Square position, boolean isActive, Color color) {
        this.position = position;
        this.isActive = isActive;
        this.color = color;
    }

    abstract void move();

    public Square getPosition() {
        return position;
    }

    public void setPosition(Square position) {
        this.position = position;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
