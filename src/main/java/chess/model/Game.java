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
    List<Point> legalPoints = new ArrayList<>();
    private Movement movement = new Movement();
    private List<Move> moves = new ArrayList<>();

    private Player playerWhite = new Player("Player 1", WHITE);
    private Player playerBlack = new Player("Player 2", BLACK);
    private Player currentPlayer;

    public void initGame() {
        board.initBoard();
        movement.setBoardMap(boardMap);
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
        if(boardMap.containsKey(clickedPoint) && markedPoint == null){
            if(!clickedOwnPiece(clickedPoint)){
                return;
            }
        }

        if (markedPoint == null) {
            markedPoint = new Point(x, y);
        }

        if (legalPoints.size() == 0 && boardMap.get(markedPoint) != null) {
            legalPoints = checkLegalMoves(boardMap.get(markedPoint), markedPoint);

            //This is needed otherwise an empty list would leave markedPiece and markedPoint as some value
            if (legalPoints.size() == 0) {
                markedPoint = null;
            }
        } else {
            if (legalPoints.contains(clickedPoint)) {
                move(clickedPoint);
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

    private boolean clickedOwnPiece(Point p){
        return boardMap.get(p).getColor() == currentPlayer.getColor();
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
}
