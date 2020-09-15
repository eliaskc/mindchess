package chess.model;

import chess.model.pieces.Piece;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {
    Square[][] squares = new Square[8][8];
    List<Piece> pieces = new ArrayList<>();
    Piece markedPiece = null;   //To find the piece you clicked on
    ImageView chessBoardImage = null;
    List<ImageView> pieceImages = new ArrayList<>();

    public List<Square> getMockLegalSquares() {
        return mockLegalSquares;
    }

    List<Square> mockLegalSquares = new ArrayList<>();

    public Board() {}

    public Square[][] getSquares() {
        return squares;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    public void setChessBoardImage(ImageView chessBoardImage) {
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
        pieceImages.clear();
        for(Piece p : pieces) {
            p.getPieceImage().setX(p.getSquare().getCoordinatesX() * 75 + 7.5);
            p.getPieceImage().setY(p.getSquare().getCoordinatesY() * 75 + 7.5);
            pieceImages.add(p.getPieceImage());
        }
    }

    void handleClick(double mouseX, double mouseY){
        Square clickedSquare = getClickedSquare(mouseX, mouseY);

        Piece clickedPiece = checkSquare(clickedSquare);
        //Objects shouldn't be null
        if(markedPiece == null) {
            markedPiece = clickedPiece;
        }

        if(mockLegalSquares.size() == 0) {
            checkLegalMoves(clickedPiece);
        } else {
            if(mockLegalSquares.contains(clickedSquare)) {
                move(markedPiece, clickedSquare);
            }
            mockLegalSquares.clear();
            markedPiece = null;
        }

        fetchPieceImages();
    }

    private Square getClickedSquare(double mouseX, double mouseY) {
        int x = 0;
        int y = 0;
        try {
            x = translateX(mouseX);
            y = translateY(mouseY);
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        return squares[x][y];
    }


    //Sets the mouse clicks x to the a value 0-7 corresponding to the correct square
    private int translateX(double x) {
        //hardcoded for now
        for (int i = 0; i < 8; i++) {
            if((i * 75 + 340 <= x && x <= 340 + 75*(i+1))){
                return i;
            }
        }
        throw new IllegalArgumentException("Outside board");
    }

    //Sets the mouse clicks y to the a value 0-7 corresponding to the correct square
    private int translateY(double y) {
        //hardcoded for now
        for (int i = 0; i < 8; i++) {
            if((i * 75 + 60 <= y && y <= 60 + 75*(i+1))){
                return i;
            }
        }
        throw new IllegalArgumentException("Outside board");
    }

    private Piece checkSquare(Square square){
        Piece returnValue = null;
        for(Piece p : pieces) {
            if(p.getSquare().equals(square)) {
                return p;
            }
        }
        return returnValue;
    }

    private void checkLegalMoves(Piece clickedPiece) {
        if(clickedPiece == null) {
            return;
        }

        //Mock
        for(Square[] array : getSquares()) {
            mockLegalSquares.addAll(Arrays.asList(array));
        }
    }

    void placeAllPieces(){
        placeBlackPieces();
        placeWhitePieces();
        System.out.println(pieces.size());
    }

    private void placeBlackPieces(){
        pieces.add(PieceFactory.createRook(getSquares()[0][0],true, Color.BLACK));
        pieces.add(PieceFactory.createKnight(getSquares()[1][0],true, Color.BLACK));
        pieces.add(PieceFactory.createBishop(getSquares()[2][0],true, Color.BLACK));
        pieces.add(PieceFactory.createQueen(getSquares()[3][0],true, Color.BLACK));
        pieces.add(PieceFactory.createKing(getSquares()[4][0],true, Color.BLACK));
        pieces.add(PieceFactory.createBishop(getSquares()[5][0],true, Color.BLACK));
        pieces.add(PieceFactory.createKnight(getSquares()[6][0],true, Color.BLACK));
        pieces.add(PieceFactory.createRook(getSquares()[7][0],true, Color.BLACK));
        for (int i = 0; i <= 7; i++) {
            pieces.add(PieceFactory.createPawn(getSquares()[i][1],true, Color.BLACK));
        }
    }
    private void placeWhitePieces(){
        pieces.add(PieceFactory.createRook(getSquares()[0][7],true, Color.WHITE));
        pieces.add(PieceFactory.createKnight(getSquares()[1][7],true, Color.WHITE));
        pieces.add(PieceFactory.createBishop(getSquares()[2][7],true, Color.WHITE));
        pieces.add(PieceFactory.createQueen(getSquares()[3][7],true, Color.WHITE));
        pieces.add(PieceFactory.createKing(getSquares()[4][7],true, Color.WHITE));
        pieces.add(PieceFactory.createBishop(getSquares()[5][7],true, Color.WHITE));
        pieces.add(PieceFactory.createKnight(getSquares()[6][7],true, Color.WHITE));
        pieces.add(PieceFactory.createRook(getSquares()[7][7],true, Color.WHITE));
        for (int i = 0; i <= 7; i++) {
            pieces.add(PieceFactory.createPawn(getSquares()[i][6],true, Color.WHITE));
        }
    }


    private void move(Piece piece, Square square) {
        //will later have to check if there is a piece on the square we want to move to
        piece.setSquare(square);
    }
}
