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
    List<Point> mockLegalPoints = new ArrayList<>();

    private Movement movement = new Movement();

    public Board() {
    }

    public Map<Point, Piece> getBoardMap() {
        return boardMap;
    }

    public List<Point> getMockLegalPoints() {
        return mockLegalPoints;
    }

    //Temporary, just used to get all points for legalMoves
    public List<Point> getAllPoints() {
        List<Point> points = new ArrayList<>();
        for (int i = 0; i < 8; i ++) {
            for (int j = 0; j < 8; j ++) {
                points.add(new Point(i,j));
            }
        }
        return points;
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
     * It receives input about the click and first fetches the clicked Point, and then the Piece on the point (if there is one).
     *
     * If no piece has been marked, it marks the Piece on the clicked Point
     *
     * If a piece has been marked already, it checks if the clicked Point is one that is legal to move to and makes the move
     * if it is.
     *
     * @param x
     * @param y
     */
    void handleBoardClick(int x, int y){
        Point clickedPoint = new Point(x, y);
        Piece clickedPiece = boardMap.get(clickedPoint);

        if(markedPiece == null && markedPoint == null) {
            markedPiece = clickedPiece;
            markedPoint = new Point(x,y);
        }

        if(mockLegalPoints.size() == 0 && markedPiece != null) {
            mockLegalPoints = checkLegalMoves(markedPiece, markedPoint);
            if(mockLegalPoints.size() == 0){ //This is needed otherwise an empty list would leave markedPiece and markedPoint as some value
                markedPiece = null;
                markedPoint = null;
            }
        } else {
            if(mockLegalPoints.contains(clickedPoint)) {
                move(markedPiece, clickedPoint);
            }
            mockLegalPoints.clear();
            markedPiece = null;
            markedPoint = null;
        }
    }

    /**
     * Checks the points which the clicked piece are allowed to move to
     * @param markedPiece the piece that was "highlighted"
     * @return  returns a list of all legal moves possible for the clicked piece
     */
    private List<Point> checkLegalMoves(Piece markedPiece, Point markedPoint) {
        return movement.pieceMoveDelegation(markedPiece, markedPoint);
    }

    /**
     * Moves the specified piece to the specified point
     *
     * **UNFINISHED**
     */
    private void move(Piece piece, Point clickedPoint) {
        boardMap.put(clickedPoint, boardMap.get(markedPoint));
        boardMap.remove(markedPoint);
        boardMap.remove(new Point(0,0));
        //Currently two bugs: cant move piece on top of another piece and cant move current piece to same point
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
