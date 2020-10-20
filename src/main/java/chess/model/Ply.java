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
    private Square movedFrom;
    private Square movedTo;
    private IPiece movedPiece;
    private IPiece takenPiece;
    private Map<Square, IPiece> boardSnapshot;

    public Ply(String playerName, Square movedFrom, Square movedTo, IPiece movedPiece, IPiece takenPiece, Map<Square, IPiece> boardMap) {
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

    public IPiece getMovedPiece() {
        return movedPiece;
    }

    public IPiece getTakenPiece() {
        return takenPiece;
    }

    public Map<Square, IPiece> getBoardSnapshot() {
        return boardSnapshot;
    }
}
