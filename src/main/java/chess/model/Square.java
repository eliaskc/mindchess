package chess.model;

import chess.model.pieces.Piece;

import java.awt.*;

public class Square {
    Point coordinates = new Point(0, 0);

    Piece piece = null;

    public Square(int x, int y) {
        this.coordinates.x = x;
        this.coordinates.y = y;
    }

    public int getCoordinatesX() {
        return coordinates.x;
    }
    public int getCoordinatesY() {
        return coordinates.y;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }
    public String getPieceName() {
        if(piece == null){
            return "nothing";
        }
        return piece.getName();
    }

}
