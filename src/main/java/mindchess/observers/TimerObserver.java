package mindchess.observers;

/**
 * Observer that notifies when something happens with the timer.
 */
public interface TimerObserver {
    void updateTimer();

    void notifyTimerEnded();
}
