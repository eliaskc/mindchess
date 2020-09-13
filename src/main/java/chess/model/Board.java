package chess.model;

import chess.model.pieces.Piece;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class Board {
    Square[][] squares = new Square[8][8];
    List<Piece> pieces = new ArrayList<>();
    Piece markedPiece = null;   //To find the piece you clicked on
    ImageView chessBoardImage = null;
    List<ImageView> pieceImages = new ArrayList<>();

    public Board() {}

    //should be used to move. FIX
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

    public void setChessBoardImage(ImageView chessBoard) {
        this.chessBoardImage = chessBoardImage;
    }

    public List<ImageView> getPieceImages() {
        return pieceImages;
    }

    //For enabling resizing of the board/window
//    private double getSquareDimension(){}

//    private double getBoardX(){}

//    private double getBoardY(){}

    void initializeBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j] = new Square(i, j);
            }
        }
    }

    void fetchPieceImages() {
        for(Piece p : pieces) {
            p.getPieceImage().setX(p.getPosition().getCoordinatesX() * 75);
            p.getPieceImage().setY(p.getPosition().getCoordinatesY() * 75);
            pieceImages.add(p.getPieceImage());
        }
    }
}
