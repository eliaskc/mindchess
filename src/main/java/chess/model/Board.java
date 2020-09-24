package chess.model;

import java.awt.*;
import java.util.*;
import java.util.List;

import static chess.model.Color.*;
import static chess.model.PieceType.*;

/**
 * Board represents the chess board and contains the information and methods to interact with the chess board
 */
public class Board {
    private Piece markedPiece = null;   //To "mark" a piece
    private Point markedPoint = null;
    private Map<Point, Piece> boardMap = new HashMap<>();
    List<Point> mockLegalSquares = new ArrayList<>();

    private Movement movement = new Movement();

    public Board() {
    }

    public Map<Point, Piece> getBoardMap() {
        return boardMap;
    }

    public List<Point> getMockLegalSquares() {
        return mockLegalSquares;
    }

    //Temporary, just used to get all squares for legalMoves
    public List<Point> getAllSquares() {
        List<Point> squares = new ArrayList<>();
        for (int i = 0; i < 8; i ++) {
            for (int j = 0; j < 8; j ++) {
                squares.add(new Point(i,j));
            }
        }
        return squares;
    }

    public List<Piece> getPieces() {
        return new ArrayList<>(boardMap.values());
    }

    public void initBoard(){
        placeAllPieces();
        movement.setBoardMap(boardMap);
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
        Point clickedSquare = new Point(x, y);
        Piece clickedPiece = boardMap.get(clickedSquare);

        if(markedPiece == null && markedPoint == null) {
            markedPiece = clickedPiece;
            markedPoint = new Point(x,y);
        }

        if(mockLegalSquares.size() == 0 && markedPiece != null) {
            mockLegalSquares = checkLegalMoves(markedPiece, markedPoint);
            if(mockLegalSquares.size() == 0){ //This is needed otherwise an empty list would leave markedPiece and markedPoint as some value
                markedPiece = null;
                markedPoint = null;
            }
        } else {
            if(mockLegalSquares.contains(clickedSquare)) {
                move(markedPiece, clickedSquare);
            }
            mockLegalSquares.clear();
            markedPiece = null;
            markedPoint = null;
        }
    }

    /**
     * Checks the squares which the clicked piece are allowed to move to
     * @param markedPiece the piece that was "highlighted"
     * @return  returns a list of all legal moves possible for the clicked piece
     */
    private List<Point> checkLegalMoves(Piece markedPiece, Point markedPoint) {
        if (markedPiece.getPieceType() == QUEEN) { //Test
            return new ArrayList<>(movement.legalMovesQueen(markedPiece, markedPoint));
        } else {
            return getAllSquares();
        }
    }

    /**
     * Moves the specified piece to the specified square
     *
     * **UNFINISHED**
     */
    private void move(Piece piece, Point clickedSquare) {
        boardMap.put(clickedSquare, boardMap.get(markedPoint));
        boardMap.remove(markedPoint);
        //Currently two bugs: cant move piece on top of another piece and cant move current piece to same square
        //This is fine because it shouldn't be allowed anyway, and will be handled by the checkLegal moves in movement
    }

    /**
     * places all pieces on the board
     */
    private void placeAllPieces(){
        placeBlackPieces();
        placeWhitePieces();
    }

    /**
     * places all black pieces
     */
    private void placeBlackPieces(){
        boardMap.put(new Point(0,0), new Piece(true, BLACK, ROOK));
        boardMap.put(new Point(1,0), new Piece(true, BLACK, KNIGHT));
        boardMap.put(new Point(2,0), new Piece(true, BLACK, BISHOP));
        boardMap.put(new Point(3,0), new Piece(true, BLACK, QUEEN));
        boardMap.put(new Point(4,0), new Piece(true, BLACK, KING));
        boardMap.put(new Point(5,0), new Piece(true, BLACK, BISHOP));
        boardMap.put(new Point(6,0), new Piece(true, BLACK, KNIGHT));
        boardMap.put(new Point(7,0), new Piece(true, BLACK, ROOK));
        for (int i = 0; i <= 7; i++) {
            boardMap.put(new Point(i,1), new Piece(true, BLACK, PAWN));
        }
    }

    /**
     * places all white pieces
     */
    private void placeWhitePieces(){
        for (int i = 0; i <= 7; i++) {
            boardMap.put(new Point(i,6), new Piece(true, WHITE, PAWN));
        }
        boardMap.put(new Point(0,7), new Piece(true, WHITE, ROOK));
        boardMap.put(new Point(1,7), new Piece(true, WHITE, KNIGHT));
        boardMap.put(new Point(2,7), new Piece(true, WHITE, BISHOP));
        boardMap.put(new Point(3,7), new Piece(true, WHITE, QUEEN));
        boardMap.put(new Point(4,7), new Piece(true, WHITE, KING));
        boardMap.put(new Point(5,7), new Piece(true, WHITE, BISHOP));
        boardMap.put(new Point(6,7), new Piece(true, WHITE, KNIGHT));
        boardMap.put(new Point(7,7), new Piece(true, WHITE, ROOK));
    }
}
