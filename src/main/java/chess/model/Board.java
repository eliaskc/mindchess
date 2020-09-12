package chess.model;

import chess.model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class Board {
    Square[][] squares = new Square[8][8];
    List<Piece> pieces = new ArrayList<>();
    Piece markedPiece = null;                                               //To find the piece you clicked on

    public Board() {}

    public Square[][] getSquares() {
        return squares;
    }

    public void setSquares(Square[][] squares) {
        this.squares = squares;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public void setMarkedPiece(Piece markedPiece) {
        this.markedPiece = markedPiece;
    }

    public Piece getMarkedPiece() {
        return markedPiece;
    }

    //For testing before real tests
    public String getMarkedStatus() {
        if (markedPiece == null){
            return "null";
        }
        return (markedPiece.getPosition().getCoordinatesX() + " " + markedPiece.getPosition().getCoordinatesY());
    }
}
