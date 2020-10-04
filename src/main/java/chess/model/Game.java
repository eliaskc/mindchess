package chess.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static chess.model.Color.*;

public class Game {
    private Board board = new Board();
    private Movement movement = new Movement();
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
        movement.setBoardMap(boardMap);
        movement.setPlies(plies);
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

            //This is needed otherwise an empty list would leave markedPiece and markedPoint as some value
            if (legalPoints.size() == 0) {
                markedPoint = null;
            }
        } else {
            if (legalPoints.contains(clickedPoint)) {
                plies.add(new Ply(markedPoint, clickedPoint, boardMap.get(markedPoint), currentPlayer));
                move(markedPoint, clickedPoint);
                makeSpecialMoves(markedPoint, clickedPoint);
                switchPlayer();
            }
            legalPoints.clear();
            markedPoint = null;
        }
    }


    /**
     * Checks the points which the clicked piece are allowed to move to
     *
     * @param markedPiece the piece that was "highlighted"
     * @return returns a list of all legal moves possible for the clicked piece
     */
    private List<Point> checkLegalMoves(Piece markedPiece, Point markedPoint) {
        return movement.pieceMoveDelegation(markedPiece, markedPoint);
    }

    /**
     * Checks if any special moves are attempted and if so, makes the necessary actions
     * @param markedPoint
     * @param clickedPoint
     */
    private void makeSpecialMoves(Point markedPoint, Point clickedPoint) {
        if (movement.getCastlingPoints().size() != 0 && movement.getCastlingPoints().contains(clickedPoint)) {
            if (clickedPoint.x > markedPoint.x) {
                move(new Point(clickedPoint.x + 1, clickedPoint.y), new Point(clickedPoint.x - 1, clickedPoint.y));
            } else if (clickedPoint.x < markedPoint.x) {
                move(new Point(clickedPoint.x - 2, clickedPoint.y), new Point(clickedPoint.x + 1, clickedPoint.y));
            }
        }

    }


    /**
     * Moves the marked piece to the clicked point
     * <p>
     */
    private void move(Point moveFrom, Point moveTo) {
        if (boardMap.get(moveTo) != null) {
            deadPieces.add(boardMap.get(moveTo));
        }

        boardMap.put(moveTo, boardMap.get(moveFrom));
        boardMap.remove(moveFrom);
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

    /**
     * checks if a piece from a specified point has moved
     * @param point
     * @return true if the piece on the specified point has moved
     */
    private boolean pieceOnPointHasMoved(Point point) {
        if(boardMap.get(point) != null && pliesContainsPiece(boardMap.get(point))){
            return true;
        }
        return false;
    }

    /**
     * checking if a piece exists in the plies list
     * @param piece
     * @return true if it exists
     */
    private boolean pliesContainsPiece(Piece piece) {
        for(Ply p : plies){
            if(p.getMovedPiece() == piece) return true;
        }
        return false;
    }
}
