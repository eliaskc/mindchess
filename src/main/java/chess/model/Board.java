package chess.model;

import javafx.scene.image.ImageView;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static chess.model.Color.*;
import static chess.model.PieceType.*;

/**
 * Board represents the chess board and contains the information and methods to interact with the chess board
 */
public class Board {
    Square[][] squares = new Square[8][8];
    List<Piece> pieces = new ArrayList<>();
    Piece markedPiece = null;   //To "mark" a piece

    public List<Square> getMockLegalSquares() {
        return mockLegalSquares;
    }

    List<Square> mockLegalSquares = new ArrayList<>();

    public Board() {
        initializeBoard();
    }

    public Square[][] getSquares() {
        return squares;
    }

    public List<Piece> getPieces() {
        return pieces;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }

    /**
     * creates a 8x8 matrix with squares representing the chess board
     */
    private void initializeBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                squares[i][j] = new Square(i, j);
            }
        }
    }


    /**
     * handleBoardClick() is the method responsible for investigating clicks on the board and deciding what should be done.
     *
     * It receives input about the click and first fetches the clicked Square, and then the Piece on the square (if there is one).
     *
     * If no piece has been marked, it marks the Piece on the clicked Square
     *
     * If a piece has been marked already, it checks if the clicked Square is one that is legal to move to and makes the move
     * if it is.
     *
     * @param x
     * @param y
     */
    void handleBoardClick(int x, int y){
        Square clickedSquare = null;
        try {
            clickedSquare = getClickedSquare(x, y);
        } catch (NullPointerException e) {
            System.out.println(e);
        }

        Square finalClickedSquare = clickedSquare;
        Optional<Piece> tempClickedPiece = pieces.stream().filter(p -> p.getSquare().equals(finalClickedSquare)).findFirst();
        Piece clickedPiece;
        clickedPiece = tempClickedPiece.orElse(null);

        if(markedPiece == null) {
            markedPiece = clickedPiece;
        }

        if(mockLegalSquares.size() == 0 && clickedPiece != null) {
            mockLegalSquares = checkLegalMoves(clickedPiece);
        } else {
            if(mockLegalSquares.contains(clickedSquare)) {
                move(markedPiece, clickedSquare);
            }
            mockLegalSquares.clear();
            markedPiece = null;
        }
    }

    /**
     * Translates the input coordinates into a square
     *
     * Throws an exception if the coordinate is out of bounds(larger or smaller than the board)
     *
     * @param x
     * @param y
     * @return  the square corresponding to the coordinates given.
     */
    private Square getClickedSquare(int x, int y) {
        if (squares[x][y] == null){
            throw new NullPointerException("Clicked outside board");
        }
        return squares[x][y];
    }

    /**
     * Checks the squares which the clicked piece are allowed to move to
     * @param clickedPiece the piece that has been clicked on most recently
     * @return  returns a list of all legal moves possible for the clicked piece
     */
    private List<Square> checkLegalMoves(Piece clickedPiece) {
        if(clickedPiece == null) {
            throw new IllegalArgumentException("No piece given");
        }

        List<Square> returnList = new ArrayList<>();
        //Mock
        for(Square[] array : getSquares()) {
            returnList.addAll(Arrays.asList(array));
        }
        return returnList;
    }

    /**
     * Moves the specified piece to the specified square
     *
     * **UNFINISHED**
     *
     * @param piece
     * @param square
     */
    private void move(Piece piece, Square square) {
        //will later have to check if there is a piece on the square we want to move to
        piece.setSquare(square);
    }

    /**
     * places all pieces on the board
     */
    void placeAllPieces(){
        placeBlackPieces();
        placeWhitePieces();
    }

    /**
     * places all black pieces, uses a factory
     */
    private void placeBlackPieces(){
        pieces.add( new Piece(getSquares()[0][0],true, BLACK, ROOK));
        pieces.add( new Piece(getSquares()[1][0],true, BLACK, KNIGHT));
        pieces.add( new Piece(getSquares()[2][0],true, BLACK, BISHOP));
        pieces.add( new Piece(getSquares()[3][0],true, BLACK, QUEEN));
        pieces.add( new Piece(getSquares()[4][0],true, BLACK, KING));
        pieces.add( new Piece(getSquares()[5][0],true, BLACK, BISHOP));
        pieces.add( new Piece(getSquares()[6][0],true, BLACK, KNIGHT));
        pieces.add( new Piece(getSquares()[7][0],true, BLACK, ROOK));
        for (int i = 0; i <= 7; i++) {
            pieces.add( new Piece(getSquares()[i][1],true, BLACK, PAWN));
        }
    }
    /**
     * places all white pieces, uses a factory
     */
    private void placeWhitePieces(){
        pieces.add( new Piece(getSquares()[0][7],true, WHITE, ROOK));
        pieces.add( new Piece(getSquares()[1][7],true, WHITE, KNIGHT));
        pieces.add( new Piece(getSquares()[2][7],true, WHITE, BISHOP));
        pieces.add( new Piece(getSquares()[3][7],true, WHITE, QUEEN));
        pieces.add( new Piece(getSquares()[4][7],true, WHITE, KING));
        pieces.add( new Piece(getSquares()[5][7],true, WHITE, BISHOP));
        pieces.add( new Piece(getSquares()[6][7],true, WHITE, KNIGHT));
        pieces.add( new Piece(getSquares()[7][7],true, WHITE, ROOK));
        for (int i = 0; i <= 7; i++) {
            pieces.add( new Piece(getSquares()[i][6],true, WHITE, PAWN));
        }
    }



}
