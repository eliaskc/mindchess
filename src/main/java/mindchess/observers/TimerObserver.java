package mindchess.observers;

/**
 * Observer that notifies when something happens with the timer.
 *
 * @author Erik Wessman, Elias Carlson, Elias Hallberg, Arvid Holmqvist
 */
public interface TimerObserver {
    void updateTimer();

    void notifyTimerEnded();
}
