package chess.model;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Representation of a move by either Player. In chess terms a half-move, or a ply
 */
public class Ply {
    private String playerName;
    private Square movedFrom;
    private Square movedTo;
    private Piece movedPiece;
    private Piece takenPiece;
    private Map<Square, Piece> boardSnapshot;

    public Ply(String playerName, Square movedFrom, Square movedTo, Piece movedPiece, Piece takenPiece, Map<Square, Piece> boardMap) {
        this.playerName = playerName;
        this.movedFrom = movedFrom;
        this.movedTo = movedTo;
        this.movedPiece = movedPiece;
        this.takenPiece = takenPiece;
        this.boardSnapshot = new HashMap<>(boardMap);
    }

    public String getPlayerName() {
        return playerName;
    }

    public Square getMovedFrom() {
        return movedFrom;
    }

    public Square getMovedTo() {
        return movedTo;
    }

    public Piece getMovedPiece() {
        return movedPiece;
    }

    public Piece getTakenPiece() {
        return takenPiece;
    }

    public Map<Square, Piece> getBoardSnapshot() {
        return boardSnapshot;
    }
}
