package chess.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Class Player represents a player playing chess and contains attributes for that player
 */
public class Player {
    private final ChessTimer chessTimer = new ChessTimer();
    private List<Piece> pieces = new ArrayList<>();
    private String name;
    private final Color color;

    public Player(String name, Color color) {
        this.name = name;
        this.color = color;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Color getColor() {
        return color;
    }

    public ChessTimer getTimer() {
        return chessTimer;
    }

    public void setPieces(List<Piece> pieces) {
        this.pieces = pieces;
    }
}
