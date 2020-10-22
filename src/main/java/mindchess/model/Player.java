package mindchess.model;

import mindchess.observers.TimerObserver;

/**
 * Class Player represents a player playing mindchess and contains attributes for that player
 * <p>
 * The player controls its own timer
 */
public class Player implements IPlayer {
    private final ChessTimer chessTimer = new ChessTimer();
    private final ChessColor chessColor;
    private PlayerType playerType;
    private String name;

    Player(String name, ChessColor chessColor, PlayerType playerType, Integer gameLength) {
        this.name = name;
        this.chessColor = chessColor;
        this.playerType = playerType;
        this.chessTimer.setTime(gameLength);
    }
 
    //-------------------------------------------------------------------------------------
    //Timer
    public void startPlayerTimer() {
        chessTimer.startTimer();
    }

    public void stopPlayerTimer() {
        chessTimer.stopTimer();
    }

    //-------------------------------------------------------------------------------------
    //Observer
    public void addTimerObserver(TimerObserver observer) {
        chessTimer.addObserver(observer);
    }

    //-------------------------------------------------------------------------------------
    //Getters
    public String getName() {
        return name;
    }

    public int getCurrentTime() {
        return chessTimer.getTime();
    }

    public ChessColor getColor() {
        return chessColor;
    }
    
    public PlayerType getPlayerType() {
        return playerType;
    }

    //-------------------------------------------------------------------------------------
    //Setters
    public void setTimerActive(boolean active) {
        chessTimer.setActive(active);
    }
}
