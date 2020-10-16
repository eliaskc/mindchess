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
public class ChessFacade {
    private Game currentGame;
    private final List<Game> gameList = new ArrayList<>();

    public String getCurrentPlayerBlackName(){
        return currentGame.getPlayerBlack().getName();
    }

    public String getCurrentPlayerWhiteName(){
        return currentGame.getPlayerWhite().getName();
    }

    public void setCurrentPlayerWhiteName(String name){
        currentGame.getPlayerWhite().setName(name);
    }

    public void setCurrentPlayerBlackName(String name){
        currentGame.getPlayerBlack().setName(name);
    }

    public boolean isCurrentPlayerWhite(){
        return currentGame.getCurrentPlayer().equals(currentGame.getPlayerWhite());
    }

    public Game getCurrentGame() {
        return currentGame;
    }

    public List<Game> getGameList() {
        return new ArrayList<>(gameList);
    }

    public int getCurrentWhiteTimerTime(){
       return currentGame.getPlayerWhite().getCurrentTime();
    }

    public int getCurrentBlackTimerTime(){
        return currentGame.getPlayerBlack().getCurrentTime();
    }

    public void setCurrentWhitePlayerTimerTime(int seconds){
        currentGame.getPlayerWhite().setTime(seconds);
    }

    public void setCurrentBlackPlayerTimerTime(int seconds){
        currentGame.getPlayerBlack().setTime(seconds);
    }


    /**
     * sends the coordinates from the mouse click to the board to handle and notifies all observers a click has been made
     *
     * @param x the x coordinate for the mouse when it clicks
     * @param y the y coordinate for the mouse when it clicks
     */
    public void handleBoardInput(int x, int y) {
        currentGame.handleBoardInput(x, y);
    }

    //-------------------------------------------------------------------------------------
    //Game

    public void createNewGame() {
        currentGame = new Game();
        currentGame.initGame();
        gameList.add(currentGame);
    }

    public void stopAllTimers(){
        currentGame.stopAllTimers();
    }
}
