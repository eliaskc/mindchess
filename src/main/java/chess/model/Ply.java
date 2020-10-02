package chess.model;

import java.awt.*;

/**
 * Representation of a move by either Player. In chess terms a half-move, or a ply
 */
public class Ply {
    Point movedFrom;
    Point movedTo;
    Piece movedPiece;
    Player player;

    public Ply(Point movedFrom, Point movedTo, Piece movedPiece, Player player) {
        this.movedFrom = movedFrom;
        this.movedTo = movedTo;
        this.movedPiece = movedPiece;
        this.player = player;
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

    public Player getPlayer() {
        return player;
    }
}