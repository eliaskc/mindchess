package chess.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ChessTimer handles timers
 * <p>
 * The timer counts down from a specified number in seconds and updates its observers every second
 * <p>
 * It allows the manipulation of timers by pausing and unpausing.
 */
public class ChessTimer {
    private int time;
    private boolean active = false;
    private Timer timer = new Timer();
    private final List<TimerObserver> observers = new ArrayList<>();

    public ChessTimer() {

    }

    /**
     * stops previous timer to make sure that not more than 1 threads are active
     * <p>
     * Creates and starts decrementing a new timer
     */
    public void startTimer() {
        stopTimer();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (active) decrementTime();
                if (getTime() <= 0) stopTimer();
            }
        }, 0, 1000);
    }

    /**
     * stops timer,
     */
    private void stopTimer() {
        timer.cancel();
        setActive(false);
        for (TimerObserver o : observers) {
            o.timerGameEnd();
        }
    }

    /**
     * decrements the time Integer
     */
    private void decrementTime() {
        time--;
        notifyObservers();
    }

    /**
     * Notifies observers every second
     */
    private void notifyObservers() {
        for (TimerObserver o : observers) {
            o.updateTimer();
        }
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void addObserver(TimerObserver t) {
        observers.add(t);
    }


}
