package chess.model;

import chess.GameObserver;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static chess.model.Color.BLACK;
import static chess.model.Color.WHITE;

public class Game implements TimerObserver {
    private final List<GameObserver> gameObservers = new ArrayList<>();

    private final Board board = new Board();
    private final Movement movement = new Movement();
    private final Map<Point, Piece> boardMap = board.getBoardMap();

    private Point markedPoint = null;

    private final List<Piece> deadPieces = new ArrayList<>();
    private final List<Point> legalPoints = new ArrayList<>();
    private final List<Ply> plies = new ArrayList<>();

    private final Player playerWhite = new Player("Player 1", WHITE);
    private final Player playerBlack = new Player("Player 2", BLACK);
    private Player currentPlayer;

    public void initGame() {
        board.initBoard();
        movement.setBoardMap(boardMap);
        movement.setPlies(plies);
        playerWhite.setPieces(board.getPiecesByColor(WHITE));
        playerBlack.setPieces(board.getPiecesByColor(BLACK));
        currentPlayer = playerWhite;
    }

    public void initTimers() {
        playerWhite.getTimer().addObserver(this);
        playerBlack.getTimer().addObserver(this);
        playerWhite.getTimer().startTimer();
        playerWhite.getTimer().setActive(true);
        playerBlack.getTimer().startTimer();
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
        if (clickedOpponentsPiece(clickedPoint)) {
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
                makeSpecialMoves(markedPoint, clickedPoint);
                move(markedPoint, clickedPoint);
                switchPlayer();
                winConditionCheck();
            }
            legalPoints.clear();
            markedPoint = null;
        }

        notifyDrawLegalMoves();
    }

    private void winConditionCheck(){
        checkKingTaken();
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
     *
     * @param markedPoint
     * @param clickedPoint
     */
    private void makeSpecialMoves(Point markedPoint, Point clickedPoint) {
        //castling
        if (movement.getCastlingPoints().size() != 0 && movement.getCastlingPoints().contains(clickedPoint)) {
            if (clickedPoint.x > markedPoint.x) {
                move(new Point(clickedPoint.x + 1, clickedPoint.y), new Point(clickedPoint.x - 1, clickedPoint.y));
            } else if (clickedPoint.x < markedPoint.x) {
                move(new Point(clickedPoint.x - 2, clickedPoint.y), new Point(clickedPoint.x + 1, clickedPoint.y));
            }
        }

        //en passant
        if (movement.getEnPassantPoints().size() != 0 && movement.getEnPassantPoints().contains(clickedPoint)) {
            if (boardMap.get(markedPoint).getColor() == WHITE) {
                takePiece(new Point(clickedPoint.x, clickedPoint.y + 1));
            } else if (boardMap.get(markedPoint).getColor() == BLACK) {
                takePiece(new Point(clickedPoint.x, clickedPoint.y - 1));
            }
        }
    }

    private boolean clickedOpponentsPiece(Point p) {
        if (boardMap.containsKey(p) && markedPoint == null) {
            return !(boardMap.get(p).getColor() == currentPlayer.getColor());
        }
        return false;
    }

    /**
     * Moves the marked piece to the clicked point
     * <p>
     */
    private void move(Point moveFrom, Point moveTo) {
        if (boardMap.get(moveTo) != null) {
            takePiece(moveTo);
        }

        boardMap.put(moveTo, boardMap.get(moveFrom));
        boardMap.remove(moveFrom);
        notifyDrawPieces();
    }

    private void takePiece(Point pointToTake) {
        deadPieces.add(boardMap.remove(pointToTake));
        notifyDrawDeadPieces();
    }

    private void checkKingTaken(){
        if (!(deadPieces.size() == 0)) {
            Piece lastPieceTaken = deadPieces.get(deadPieces.size()-1);
            if (lastPieceTaken.getPieceType() == PieceType.KING) {
                if (lastPieceTaken.getColor() == BLACK) whitePlayerWin();
                else if (lastPieceTaken.getColor() == WHITE) blackPlayerWin();
            }
        }
    }

    private void whitePlayerWin(){
        notifyEndGameObservers("white");
    }
    private void blackPlayerWin(){
        notifyEndGameObservers("black");
    }

    void notifyEndGameObservers(String result){
        gameObservers.forEach(p -> {
            p.checkEndGame(result);
        });
    }

    private void switchPlayer() {
        currentPlayer.getTimer().setActive(false);
        if (currentPlayer == playerWhite) {
            currentPlayer = playerBlack;
        } else if (currentPlayer == playerBlack) {
            currentPlayer = playerWhite;
        }
        currentPlayer.getTimer().setActive(true);
        notifySwitchedPlayer();
    }

    void stopAllTimers(){
        playerBlack.getTimer().setActive(false);
        playerWhite.getTimer().setActive(false);
    }

    @Override
     public void updateTimer() {
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.updateTimer();
        }
    }

    public void timerGameEnd() {
        if(playerWhite.getTimer().getTime()==0){
            notifyEndGameObservers("black");
        } else if (playerBlack.getTimer().getTime()==0) {
            notifyEndGameObservers("white");
        }
    }

    private void notifyDrawPieces() {
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.drawPieces();
        }
    }

    private void notifyDrawDeadPieces() {
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.drawDeadPieces();
        }
    }

    private void notifyDrawLegalMoves() {
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.drawLegalMoves();
        }
    }

    private void notifySwitchedPlayer() {
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.switchedPlayer();
        }
    }

    private void notifyPawnPromotion() {
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.pawnPromotion();
        }
    }

    public void addObserver(GameObserver gameObserver) {
        gameObservers.add(gameObserver);
    }

    public void removeObserver(GameObserver gameObserver) {
        gameObservers.remove(gameObserver);
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
}
