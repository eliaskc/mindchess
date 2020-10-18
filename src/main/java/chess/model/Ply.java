package chess.model;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Representation of a move by either Player. In chess terms a half-move, or a ply
 */
public class Ply {
    private String playerName;
    private Point movedFrom;
    private Point movedTo;
    private Piece movedPiece;
    private Piece takenPiece;
    private Map<Point, Piece> boardSnapshot;

    public Ply(String playerName, Point movedFrom, Point movedTo, Piece movedPiece, Piece takenPiece, Map<Point, Piece> boardMap) {
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

    public Point getMovedFrom() {
        return movedFrom;
    }

    public Point getMovedTo() {
        return movedTo;
    }

    public Piece getMovedPiece() {
        return movedPiece;
    }

    public Piece getTakenPiece() {
        return takenPiece;
    }

    public Map<Point, Piece> getBoardSnapshot() {
        return boardSnapshot;
    }
}
