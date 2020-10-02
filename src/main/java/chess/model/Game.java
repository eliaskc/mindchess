package chess.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static chess.model.Color.*;

public class Game {
    private Board board = new Board();
    private Map<Point, Piece> boardMap = board.getBoardMap();
    private Point markedPoint = null;

    private List<Piece> deadPieces = new ArrayList<>();
    private List<Point> legalPoints = new ArrayList<>();
    private List<Ply> plies = new ArrayList<>();
    private List<Point> specialMoves = new ArrayList<>();

    private Player playerWhite = new Player("Player 1", WHITE);
    private Player playerBlack = new Player("Player 2", BLACK);
    private Player currentPlayer;

    public void initGame() {
        board.initBoard();
        playerWhite.setPieces(board.getPiecesByColor(WHITE));
        playerBlack.setPieces(board.getPiecesByColor(BLACK));
        currentPlayer = playerWhite;
    }

    /**
     * handleBoardClick() is the method responsible for investigating clicks on the board and deciding what should be done.
     * <p>
     * It receives input about the click and first fetches the clicked Point, and then the Piece on the point (if there is one).
     * <p>
     * If no piece has been marked, it marks the Piece on the clicked Point
     * <p>
     * If a piece has been marked already, it checks if the clicked Point is one that is legal to move to and makes the move
     * if it is.
     *
     * @param x
     * @param y
     */
    void handleBoardClick(int x, int y) {
        Point clickedPoint = new Point(x, y);

        //If you click on a piece that doesn't belong to you (and no piece is marked), the click is ignored
        if(clickedOpponentsPiece(clickedPoint)) {
            return;
        }

        if (markedPoint == null) {
            markedPoint = new Point(x, y);
        }

        if (legalPoints.size() == 0 && boardMap.get(markedPoint) != null) {
            legalPoints.addAll(checkLegalMoves(boardMap.get(markedPoint), markedPoint));
            specialMoves = getSpecialMoves(boardMap.get(markedPoint),markedPoint);
            legalPoints.addAll(specialMoves);

            //This is needed otherwise an empty list would leave markedPiece and markedPoint as some value
            if (legalPoints.size() == 0) {
                markedPoint = null;
            }
        } else {
            if(specialMoves.contains(clickedPoint)){
                plies.add(new Ply(markedPoint, clickedPoint, boardMap.get(markedPoint), currentPlayer));
                move(clickedPoint);
                moveCastleRook(markedPoint,clickedPoint);
                switchPlayer();
            }else if (legalPoints.contains(clickedPoint)) {
                plies.add(new Ply(markedPoint, clickedPoint, boardMap.get(markedPoint), currentPlayer));
                move(clickedPoint);
                switchPlayer();
            }
            legalPoints.clear();
            markedPoint = null;
        }
    }

    private void moveCastleRook(Point kingOldPoint,Point kingNewPoint){
        if(kingNewPoint.getX() > kingOldPoint.getX()){
            Point rookOldPoint = new Point(kingNewPoint.x + 1, kingNewPoint.y); //Rooks position
            Point rookNewPoint = new Point(kingNewPoint.x - 1, kingNewPoint.y); //Rooks destination
            plies.add(new Ply(rookOldPoint, rookNewPoint, boardMap.get(rookOldPoint), currentPlayer));
            move(rookOldPoint, rookNewPoint);
        } else {
            Point rookOldPoint = new Point(kingNewPoint.x - 1, kingNewPoint.y); //Rooks position
            Point rookNewPoint = new Point(kingNewPoint.x + 1, kingNewPoint.y); //Rooks destination
            plies.add(new Ply(rookOldPoint, rookNewPoint, boardMap.get(rookOldPoint), currentPlayer));
            move(rookOldPoint, rookNewPoint);
        }
    }

    /**
     * Checks the points which the clicked piece are allowed to move to
     *
     * @param markedPiece the piece that was "highlighted"
     * @return returns a list of all legal moves possible for the clicked piece
     */
    private List<Point> checkLegalMoves(Piece markedPiece, Point markedPoint) {
        //Boolean hasMoved = pieceOnPointHasMoved(markedPoint);
        return board.checkLegalMoves(markedPiece, markedPoint);
    }

    private List<Point> getSpecialMoves(Piece markedPiece, Point markedPoint){
        //castle
        List<Point> specialMoves = new ArrayList<>();
        if(markedPiece.getPieceType() == PieceType.KING && !pieceOnPointHasMoved(markedPoint)){
            //om man klickat på en kung som inte rört sig
            if(checkRightCastle(markedPiece, markedPoint)){
                specialMoves.add(new Point(markedPoint.x + 2, markedPoint.y));
            }
            if(checkLeftCastle(markedPiece, markedPoint)){
                specialMoves.add(new Point(markedPoint.x - 3, markedPoint.y));
            }
        }
        return specialMoves;
    }

    private boolean checkRightCastle (Piece markedPiece, Point markedPoint) {
        for (int i = markedPoint.x; i < markedPoint.x+2 && i < 8; i++) {
            if(!isPointUnoccupied(new Point(i+1, markedPoint.y)))return false;
        }
        if(!isPointUnoccupied(new Point(markedPoint.x+3, markedPoint.y))){
            Point p = new Point(markedPoint.x+3, markedPoint.y);
            if(boardMap.get(p).getPieceType() == PieceType.ROOK && pieceOnPointHasMoved(p)){
                System.out.println("false");
                return false;
            }
        } else {
            return false;
        }
        System.out.println("true");
        return true;
    }

    private boolean checkLeftCastle (Piece markedPiece, Point markedPoint) {
        for (int i = markedPoint.x; i > markedPoint.x-3 && i > 0; i--) {
            if(!isPointUnoccupied(new Point(i-1, markedPoint.y)))return false;
        }
        if(!isPointUnoccupied(new Point(markedPoint.x-4, markedPoint.y))){
            Point p = new Point(markedPoint.x-4, markedPoint.y);
            if(boardMap.get(p).getPieceType() == PieceType.ROOK && pieceOnPointHasMoved(p)){
                System.out.println("false");
                return false;
            }
        }
        System.out.println("true");
        return true;
    }

    private boolean isPointUnoccupied(Point p){
        if(boardMap.get(p) == null) return true;
        return false;
    }

    /**
     * Moves the specified piece to the specified point
     * <p>
     * TODO Is also going to save each move as an instance of Move
     */
    private void move(Point clickedPoint) {
        if (boardMap.get(clickedPoint) != null) {
            deadPieces.add(boardMap.get(clickedPoint));
        }

        boardMap.put(clickedPoint, boardMap.get(markedPoint));
        boardMap.remove(markedPoint);
    }

    private void move(Point pointFrom,Point pointTo){
        if(boardMap.get(pointFrom) != null){
            boardMap.put(pointTo, boardMap.get(pointFrom));
            boardMap.remove(pointFrom);
        }
    }

    private boolean clickedOpponentsPiece(Point p){
        if(boardMap.containsKey(p) && markedPoint == null){
            return !(boardMap.get(p).getColor() == currentPlayer.getColor());
        }
        return false;
    }

    private void switchPlayer() {
        currentPlayer.getTimer().setActive(false);
        if(currentPlayer == playerWhite) {
            currentPlayer = playerBlack;
        } else if (currentPlayer == playerBlack) {
            currentPlayer = playerWhite;
        }
        currentPlayer.getTimer().setActive(true);
    }

    public void initTimers(){
        playerWhite.getTimer().startTimer();
        playerWhite.getTimer().setActive(true);
        playerBlack.getTimer().startTimer();
    }

    public List<Point> getLegalPoints() {
        return legalPoints;
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayerWhite() {
        return playerWhite;
    }

    public Player getPlayerBlack() {
        return playerBlack;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public List<Piece> getDeadPieces() {
        return deadPieces;
    }

    public List<Ply> getPlies() {
        return plies;
    }

    private boolean pieceOnPointHasMoved(Point point) {
        if(boardMap.get(point) != null && pliesContainsPiece(boardMap.get(point))){
            return true;
        }
        return false;
    }

    private boolean pliesContainsPiece(Piece piece) {
        for(Ply p : plies){
            if(p.getMovedPiece() == piece) return true;
        }
        return false;
    }
}
