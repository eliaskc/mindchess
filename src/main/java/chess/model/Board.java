package chess.model;

import chess.model.pieces.Piece;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Board {
    Square[][] squares = new Square[8][8];
    List<Piece> pieces = new ArrayList<>();
    Piece markedPiece = null;   //To find the piece you clicked on
    ImageView chessBoard = null;

    public Board() {}

    public void move(Piece piece, Square square) {
        piece.setPosition(square);
    }

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

    public void setChessBoard(ImageView chessBoard) {
        this.chessBoard = chessBoard;
    }

    private double getBoardSquareX(){

    }

    private double getBoardSquareY(){
        
    }

    void initializeBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j] = new Square(i, j);
            }
        }
    }
}
