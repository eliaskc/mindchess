package mindchess.model;

import mindchess.observers.TimerObserver;

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
    private final List<TimerObserver> observers = new ArrayList<>();
    private int time;
    private boolean active = false;
    private Timer timer = new Timer();

    public ChessTimer() {
        timer.cancel();
    }

    /**
     * stops previous timer to make sure that not more than 1 threads are active
     * <p>
     * Creates a new timer with a initial int time and start decrementing the time once per second.
     * <p>
     * when the time reaches zero, the timer will become inactive and notify any observer
     */
    void startTimer() {
        timer.cancel();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                if (active) decrementTime();
                if (getTime() <= 0) timerRunOut();
            }
        }, 0, 1000);
    }

    /**
     * stops timer,
     */
    void stopTimer() {
        timer.cancel();
        setActive(false);
    }

    /**
     * when the time runs the timer will stop and any observer will be notified that the timer has run out
     */
    private void timerRunOut() {
        stopTimer();
        notifyTimerEnded();
    }

    /**
     * decrements the time Integer and notifies any observer that the time has updated every second
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

    private void notifyTimerEnded() {
        for (TimerObserver o : observers) {
            o.notifyTimerEnded();
        }
    }

    void addObserver(TimerObserver t) {
        observers.add(t);
    }

    int getTime() {
        return time;
    }

    void setTime(int time) {
        this.time = time;
    }

    void setActive(boolean active) {
        this.active = active;
    }

}
