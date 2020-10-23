package mindchess.model;

import mindchess.model.pieces.IPiece;

import java.util.HashMap;
import java.util.Map;

/**
 * Representation of a move by either Player. In chess terms a half-move, or a ply
 *
 * @author Erik Wessman, Elias Carlson, Elias Hallberg, Arvid Holmqvist
 */
public class Ply {
    private final String playerName;
    private final Square movedFrom;
    private final Square movedTo;
    private final IPiece movedPiece;
    private final IPiece takenPiece;
    private final Map<Square, IPiece> boardSnapshot;

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
