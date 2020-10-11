package chess.model;

import chess.observers.EndGameObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * Chess represents the model to the rest of the application
 * <p>
 * It also makes sure that that the model updates when something happens during runtime
 * <p>
 * (Composite pattern?)
 */
public class ChessFacade implements EndGameObserver {
    private Game currentGame;
    private final List<Game> gameList = new ArrayList<>();

    public Player getPlayerWhite() {
        return currentGame.getPlayerWhite();
    }

    public Player getPlayerBlack() {
        return currentGame.getPlayerBlack();
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public List<Game> getGameList() {
        return new ArrayList<>(gameList);
    }

    /**
     * sends the coordinates from the mouse click to the board to handle and notifies all observers a click has been made
     *
     * @param x the x coordinate for the mouse when it clicks
     * @param y the y coordinate for the mouse when it clicks
     */
    public void handleBoardClick(int x, int y) {
        currentGame.handleBoardClick(x, y);
    }

    //-------------------------------------------------------------------------------------
    //Game

    public void createNewGame() {
        currentGame = new Game();
        currentGame.initGame();
        currentGame.addEndGameObserver(this);
        gameList.add(currentGame);
    }

    @Override
    public void endGame(String result) {
        currentGame.stopAllTimers();
        currentGame = null;
    }
}
