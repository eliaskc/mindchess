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
}
