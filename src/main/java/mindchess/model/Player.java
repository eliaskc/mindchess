package mindchess.model;

import mindchess.observers.TimerObserver;

/**
 * Class Player represents a player playing mindchess and contains attributes for that player
 * <p>
 * The player controls its own timer
 */
public class Player {
    private final ChessTimer chessTimer = new ChessTimer();
    private final ChessColor chessColor;
    private String name;

    Player(String name, ChessColor chessColor) {
        this.name = name;
        this.chessColor = chessColor;
    }

    //-------------------------------------------------------------------------------------
    //Timer
    void startPlayerTimer() {
        chessTimer.startTimer();
    }

    void stopPlayerTimer() {
        chessTimer.stopTimer();
    }

    //-------------------------------------------------------------------------------------
    //Observer
    void addTimerObserver(TimerObserver observer) {
        chessTimer.addObserver(observer);
    }

    //-------------------------------------------------------------------------------------
    //Getters
    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    int getCurrentTime() {
        return chessTimer.getTime();
    }

    ChessColor getColor() {
        return chessColor;
    }

    //-------------------------------------------------------------------------------------
    //Setters
    void setTimerActive(boolean active) {
        chessTimer.setActive(active);
    }

    void setTime(int seconds) {
        chessTimer.setTime(seconds);
    }

}
