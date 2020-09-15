package chess.model;
import java.util.Timer;
import java.util.TimerTask;

public class ChessTimer {
    int time;
    Timer timer = new Timer();

    public ChessTimer() {

    }


    // To be discussed before implementation, observer again?
    public void testSetTime(int time) {
        System.out.println(time);
        int i = 0;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println(i);
            }
        }, 0, 1000);
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getTime() {
        return time;
    }
}
