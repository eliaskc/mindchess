package chess.model;

import chess.model.pieces.Piece;

import java.util.ArrayList;
import java.util.List;

public class Player {
    Timer timer = new Timer();
    List<Piece> pieces = new ArrayList<>();
    String name;

    public Player(String name) {
        this.name = name;
    }
}
