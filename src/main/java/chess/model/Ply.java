package chess.model;

import chess.model.pieces.IPiece;

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
    private IPiece movedIPiece;
    private IPiece takenIPiece;
    private Map<Point, IPiece> boardSnapshot;

    public Ply(String playerName, Point movedFrom, Point movedTo, IPiece movedIPiece, IPiece takenIPiece, Map<Point, IPiece> boardMap) {
        this.playerName = playerName;
        this.movedFrom = movedFrom;
        this.movedTo = movedTo;
        this.movedIPiece = movedIPiece;
        this.takenIPiece = takenIPiece;
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

    public IPiece getMovedPiece() {
        return movedIPiece;
    }

    public IPiece getTakenPiece() {
        return takenIPiece;
    }

    public Map<Point, IPiece> getBoardSnapshot() {
        return boardSnapshot;
    }
}
