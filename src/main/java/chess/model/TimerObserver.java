package chess.model;

interface TimerObserver {
    void updateTimer();
    void notifyTimerEnded();
}
