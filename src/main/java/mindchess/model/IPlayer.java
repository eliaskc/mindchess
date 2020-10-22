package mindchess.model;

import mindchess.model.enums.ChessColor;
import mindchess.model.enums.PlayerType;
import mindchess.observers.TimerObserver;

/**
 * Interfaces for Player classes
 */
public interface IPlayer {
    String getName();

    int getCurrentTime();

    ChessColor getColor();

    PlayerType getPlayerType();

    void setTimerActive(boolean active);

    void startPlayerTimer();

    void stopPlayerTimer();

    void addTimerObserver(TimerObserver observer);
}
