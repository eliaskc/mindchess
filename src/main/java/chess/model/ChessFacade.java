package chess.model;

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
    private Game currentGame;
    private final List<Game> gameList = new ArrayList<>();

    public ChessFacade() {
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
    }

    //-------------------------------------------------------------------------------------
    //Game

    public void createNewGame() {
        currentGame = new Game();
        currentGame.initGame();
        gameList.add(currentGame);
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public void endGame() {
        gameList.remove(currentGame);
        System.out.println(gameList.size());
    }

}
