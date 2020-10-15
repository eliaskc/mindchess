package chess.model;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Representation of a move by either Player. In chess terms a half-move, or a ply
 */
public class Ply {
    Point movedFrom;
    Point movedTo;
    Piece movedPiece;
    Map<Point, Piece> boardSnapshot = new HashMap<>();

    public Ply(Point movedFrom, Point movedTo, Piece movedPiece) {
        this.movedFrom = movedFrom;
        this.movedTo = movedTo;
        this.movedPiece = movedPiece;
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

    public void generateBoardSnapshot(Map<Point, Piece> boardMap){
        this.boardSnapshot = new HashMap<>(boardMap);
    }

    public Map<Point, Piece> getBoardSnapshot() {
        return boardSnapshot;
    }
}
