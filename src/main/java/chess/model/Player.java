package chess.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Player represents a player playing chess and contains attributes for that player
 */
public class Player {
    private final ChessTimer chessTimer = new ChessTimer();
    private String name;
    private final ChessColor chessColor;

    Player(String name, ChessColor chessColor) {
        this.name = name;
        this.chessColor = chessColor;
    }

    String getName() {
        return name;
    }

    void setName(String name) {
        this.name = name;
    }

    int getCurrentTime(){
        return chessTimer.getTime();
    }

    void setTime(int seconds){
        chessTimer.setTime(seconds);
    }

    ChessColor getColor() {
        return chessColor;
    }

    ChessTimer getTimer() {
        return chessTimer;
    }
    
}
