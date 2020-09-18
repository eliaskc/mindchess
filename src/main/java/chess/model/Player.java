package chess.model;

import chess.model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Player represents a player playing chess and contains attributes for that player
 */
public class Player {
    ChessTimer chessTimer = new ChessTimer();
    List<Piece> pieces = new ArrayList<>();
    String name;

    public Player(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimer() {
        return chessTimer.getTime();
    }

    public void setTimer(int time) {
        this.chessTimer.setTime(time);
    }
}
