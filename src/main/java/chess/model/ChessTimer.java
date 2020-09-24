package chess.model;
import chess.TimerObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class ChessTimer {
    private int time;
    private boolean active = false;
    private Timer timer = new Timer();
    private List<TimerObserver> observers = new ArrayList<>();

    public ChessTimer() {

    }

    public void startTimer() {
        stopTimer();
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(time);
                if(active)decrementTime();
                if(getTime() <= 0){
                    notifyObservers();
                    stopTimer();
                }
            }
        }, 0, 1000);
    }

    public void stopTimer(){
        timer.cancel();
        setActive(false);
    }

    private void decrementTime(){
        time--;
        System.out.println(time);
        notifyObservers();
    }

    private void notifyObservers(){
        for (TimerObserver o: observers) {
            o.updateTimer();
        }
    }


    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public void addObserver(TimerObserver t){
        observers.add(t);
    }
}
