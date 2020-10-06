package chess.model;

import chess.Observer;

import java.util.ArrayList;
import java.util.List;

/**
 * Chess represents the model to the rest of the application
 * <p>
 * It also makes sure that that the model updates when something happens during runtime
 * <p>
 * (Composite pattern?)
 */
public class ChessFacade {
    private final List<Observer> observers = new ArrayList<>();

    private Game currentGame;
    private final List<Game> gameList = new ArrayList<>();

    public ChessFacade() {
        createNewGame();
        currentGame.initGame();
    }

    public void startGame() {
    }

    public Player getPlayerWhite() {
        return currentGame.getPlayerWhite();
    }

    public Player getPlayerBlack() {
        return currentGame.getPlayerBlack();
    }

    public Game getGame() {
        return currentGame;
    }

    /**
     * sends the coordinates from the mouse click to the board to handle and notifies all observers a click has been made
     *
     * @param x the x coordinate for the mouse when it clicks
     * @param y the y coordinate for the mouse when it clicks
     */
    public void handleBoardClick(int x, int y) {
        currentGame.handleBoardClick(x, y);
        notifyAllObservers();
    }

    /**
     * Notifies all current observers when you click on the chess board
     */
    private void notifyAllObservers() {
        for (Observer observer : observers) {
            observer.onAction();
        }
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    //-------------------------------------------------------------------------------------
    //Game

    public void createNewGame() {
        currentGame = new Game();
        gameList.add(currentGame);
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void endGame() {
        gameList.remove(currentGame);
    }

}
