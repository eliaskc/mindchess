package mindchess.model;

import mindchess.observers.TimerObserver;

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
