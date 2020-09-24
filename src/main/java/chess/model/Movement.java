package chess.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

/**
 * Is responisble for finding legal moves
 */
public class Movement {
    private Map<Point, Piece> boardMap = new HashMap<>();

    //possible alternative to writing own getters and setters
//    private String _name;
//    public string Name; {get -> {thing++; return _name;}; set -> _name = value;};

    public void setBoardMap(Map<Point, Piece> boardMap) {
        this.boardMap = boardMap;
    }

    public void pieceMoveDelegation() {

    }

    public List<Point> legalMovesQueen(Piece markedPiece, Point markedPoint) {
        List<Point> points = new ArrayList<>();
        points.addAll(up(markedPiece, markedPoint, 8));
        points.addAll(down(markedPiece, markedPoint, 8));
        points.addAll(left(markedPiece, markedPoint, 8));
        points.addAll(right(markedPiece, markedPoint, 8));
        return points;
    }

    private List<Point> up(Piece markedPiece, Point markedPoint, int steps){
        List<Point> points = new ArrayList<>();

        for(int i = markedPoint.y - 1; i >= 0; i--) {
            Point p = new Point(markedPoint.x, i);
            if (boardMap.get(p) == null) {
                points.add(p);
            } else if (boardMap.get(p).getColor() != markedPiece.getColor()) {
                points.add(p);
                break;
            } else {
                break;
            }
        }

        return points;
    }

    private List<Point> down(Piece markedPiece, Point markedPoint, int steps){
        List<Point> points = new ArrayList<>();

        for(int i = markedPoint.y + 1; i < 8; i++) {
            Point p = new Point(markedPoint.x, i);
            if (boardMap.get(p) == null) {
                points.add(p);
            } else if (boardMap.get(p).getColor() != markedPiece.getColor()) {
                points.add(p);
                break;
            } else {
                break;
            }
        }

        return points;
    }

    private List<Point> left(Piece markedPiece, Point markedPoint, int steps){
        List<Point> points = new ArrayList<>();

        for(int i = markedPoint.x - 1; i >= 0; i--) {
            Point p = new Point(i, markedPoint.y);
            if (boardMap.get(p) == null) {
                points.add(p);
            } else if (boardMap.get(p).getColor() != markedPiece.getColor()) {
                points.add(p);
                break;
            } else {
                break;
            }
        }

        return points;
    }

    private List<Point> right(Piece markedPiece, Point markedPoint, int steps){
        List<Point> points = new ArrayList<>();

        for(int i = markedPoint.x + 1; i < 8; i++) {
            Point p = new Point(i, markedPoint.y);
            if (boardMap.get(p) == null) {
                points.add(p);
            } else if (boardMap.get(p).getColor() != markedPiece.getColor()) {
                points.add(p);
                break;
            } else {
                break;
            }
        }

        return points;
    }
}
