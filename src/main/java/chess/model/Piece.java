package chess.model;

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
public class Piece {
    private Square square;
    private boolean isActive;
    private Color color;
    private PieceType pieceType;
    private boolean mark = false;

    public Piece(Square square, boolean isActive, Color color, PieceType pieceType) {
        this.square = square;
        this.isActive = isActive;
        this.color = color;
        this.pieceType = pieceType;
    }

    public Square getSquare() {
        return square;
    }

    public void setSquare(Square square) {
        this.square = square;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public PieceType getPieceType() {
        return pieceType;
    }

    public void setPieceType(PieceType pieceType) {
        this.pieceType = pieceType;
    }

    public Color getColor() {
        return color;
    }
}
