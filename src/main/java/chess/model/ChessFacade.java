package chess.model;

import chess.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Chess represents the model to the rest of the application
 *
 * It also makes sure that that the model updates when something happens during runtime
 *
 * (Composite pattern?)
 */
public class ChessFacade {
    private static ChessFacade instance = null;

    private List<Observer> observers = new ArrayList<>();

    private Player player1 = new Player("Player 1");
    private Player player2 = new Player("Player 2");

    private Board board = new Board();

    private List<Move> moves = new ArrayList<>();

    public static ChessFacade getInstance() {
        if (instance == null) {
            instance = new ChessFacade();
            instance.init();
        }
        return instance;
    }

    private void init() {
        board.placeAllPieces();
        //board.fetchPieceImages();
    }

    public void startGame() {}

    public void endGame() {}

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Board getBoard() {
        return board;
    }

    /**
     * sends the coordinates from the mouse click to the board to handle and notifies all observers a click has been made
     *
     * @param x the x coordinate for the mouse when it clicks
     * @param y the y coordinate for the mouse when it clicks
     */
    public void handleBoardClick(int x, int y){
        board.handleBoardClick(x, y);

        notifyAllObservers();
    }

    /**
     * Notifies all current observers when you click on the chess board
     */
    private void notifyAllObservers() {
        for(Observer observer : observers) {
            observer.onAction();
        }
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
}
