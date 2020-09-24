package chess.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static chess.model.Color.BLACK;
import static chess.model.Color.WHITE;

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

    public List<Point> pieceMoveDelegation(Piece pieceToMove, Point markedPoint) {
        List<Point> legalMoves = new ArrayList<>();
        switch (pieceToMove.getPieceType()) {
            case ROOK:
                legalMoves.addAll(legalMovesRook(pieceToMove, markedPoint));
                break;
            case BISHOP:
                legalMoves.addAll(legalMovesBishop(pieceToMove, markedPoint));
                break;
            case KNIGHT:
                legalMoves.addAll(legalMovesKnight(pieceToMove, markedPoint));
                break;
            case QUEEN:
                legalMoves.addAll(legalMovesQueen(pieceToMove, markedPoint));
                break;
            case KING:
                legalMoves.addAll(legalMovesKing(pieceToMove, markedPoint));
                break;
            case PAWN:
                legalMoves.addAll(legalMovesPawn(pieceToMove, markedPoint));
                break;
        }
        return legalMoves;
    }

    private List<Point> legalMovesRook(Piece pieceToMove, Point markedPoint) {
        List<Point> points = new ArrayList<>();
        
        points.addAll(up(pieceToMove, markedPoint));
        points.addAll(down(pieceToMove, markedPoint));
        points.addAll(left(pieceToMove, markedPoint));
        points.addAll(right(pieceToMove, markedPoint));
        
        return points;
    }

    private List<Point> legalMovesBishop(Piece pieceToMove, Point markedPoint) {
        List<Point> points = new ArrayList<>();

        points.addAll(upLeft(pieceToMove, markedPoint));
        points.addAll(upRight(pieceToMove, markedPoint));
        points.addAll(downRight(pieceToMove, markedPoint));
        points.addAll(downLeft(pieceToMove, markedPoint));

        return points;
    }

    private List<Point> legalMovesKnight(Piece pieceToMove, Point markedPoint) {
        return null;
    }

    private List<Point> legalMovesPawn(Piece pieceToMove, Point markedPoint) {
        List<Point> points = new ArrayList<>();

        if (pieceToMove.getColor() == BLACK) {
            points.add(down(pieceToMove, markedPoint).get(0));
        } else if (pieceToMove.getColor() == WHITE) {
            points.add(up(pieceToMove, markedPoint).get(0));
        }

        return points;
    }

    private List<Point> legalMovesKing(Piece pieceToMove, Point markedPoint) {
        List<Point> points = new ArrayList<>();

        points.add(up(pieceToMove, markedPoint).get(0));
        points.add(right(pieceToMove, markedPoint).get(0));
        points.add(down(pieceToMove, markedPoint).get(0));
        points.add(left(pieceToMove, markedPoint).get(0));

        points.add(upLeft(pieceToMove, markedPoint).get(0));
        points.add(upRight(pieceToMove, markedPoint).get(0));
        points.add(downRight(pieceToMove, markedPoint).get(0));
        points.add(downLeft(pieceToMove, markedPoint).get(0));

        return points;
    }

    public List<Point> legalMovesQueen(Piece pieceToMove, Point markedPoint) {
        List<Point> points = new ArrayList<>();
        points.addAll(up(pieceToMove, markedPoint));
        points.addAll(down(pieceToMove, markedPoint));
        points.addAll(left(pieceToMove, markedPoint));
        points.addAll(right(pieceToMove, markedPoint));

        points.addAll(upLeft(pieceToMove, markedPoint));
        points.addAll(upRight(pieceToMove, markedPoint));
        points.addAll(downRight(pieceToMove, markedPoint));
        points.addAll(downLeft(pieceToMove, markedPoint));
        return points;
    }

    private List<Point> up(Piece pieceToMove, Point markedPoint){
        List<Point> points = new ArrayList<>();

        for(int i = markedPoint.y - 1; i >= 0; i--) {
            Point p = new Point(markedPoint.x, i);
            if (boardMap.get(p) == null) {
                points.add(p);
            } else if (boardMap.get(p).getColor() != pieceToMove.getColor()) {
                points.add(p);
                break;
            } else {
                break;
            }
        }

        return points;
    }

    private List<Point> down(Piece pieceToMove, Point markedPoint){
        List<Point> points = new ArrayList<>();

        for(int i = markedPoint.y + 1; i < 8; i++) {
            Point p = new Point(markedPoint.x, i);
            if (boardMap.get(p) == null) {
                points.add(p);
            } else if (boardMap.get(p).getColor() != pieceToMove.getColor()) {
                points.add(p);
                break;
            } else {
                break;
            }
        }

        return points;
    }

    private List<Point> left(Piece pieceToMove, Point markedPoint){
        List<Point> points = new ArrayList<>();

        for(int i = markedPoint.x - 1; i >= 0; i--) {
            Point p = new Point(i, markedPoint.y);
            if (boardMap.get(p) == null) {
                points.add(p);
            } else if (boardMap.get(p).getColor() != pieceToMove.getColor()) {
                points.add(p);
                break;
            } else {
                break;
            }
        }

        return points;
    }

    private List<Point> right(Piece pieceToMove, Point markedPoint){
        List<Point> points = new ArrayList<>();

        for(int i = markedPoint.x + 1; i < 8; i++) {
            Point p = new Point(i, markedPoint.y);
            if (boardMap.get(p) == null) {
                points.add(p);
            } else if (boardMap.get(p).getColor() != pieceToMove.getColor()) {
                points.add(p);
                break;
            } else {
                break;
            }
        }

        return points;
    }

    private List<Point> upLeft(Piece pieceToMove, Point markedPoint){
        List<Point> points = new ArrayList<>();

        int x = markedPoint.x;
        int y = markedPoint.y;
        Point p = new Point(x, y);

        for(int i = 0; i < 8; i++) {
            p.x--;
            p.y--;
            if(p.x >= 0 && p.y >= 0) {
                if (boardMap.get(p) == null) {
                    points.add(new Point(p.x, p.y));
                } else if (boardMap.get(p).getColor() != pieceToMove.getColor()) {
                    points.add(new Point(p.x, p.y));
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return points;
    }

    private List<Point> upRight(Piece pieceToMove, Point markedPoint){
        List<Point> points = new ArrayList<>();

        int x = markedPoint.x;
        int y = markedPoint.y;
        Point p = new Point(x, y);

        for(int i = 0; i < 8; i++) {
            p.x++;
            p.y--;
            if(p.x < 8 && p.y >= 0) {
                if (boardMap.get(p) == null) {
                    points.add(new Point(p.x, p.y));
                } else if (boardMap.get(p).getColor() != pieceToMove.getColor()) {
                    points.add(new Point(p.x, p.y));
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return points;
    }

    private List<Point> downRight(Piece pieceToMove, Point markedPoint){
        List<Point> points = new ArrayList<>();

        int x = markedPoint.x;
        int y = markedPoint.y;
        Point p = new Point(x, y);

        for(int i = 0; i < 8; i++) {
            p.x++;
            p.y++;
            if(p.x < 8 && p.y < 8) {
                if (boardMap.get(p) == null) {
                    points.add(new Point(p.x, p.y));
                } else if (boardMap.get(p).getColor() != pieceToMove.getColor()) {
                    points.add(new Point(p.x, p.y));
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return points;
    }

    private List<Point> downLeft(Piece pieceToMove, Point markedPoint){
        List<Point> points = new ArrayList<>();

        int x = markedPoint.x;
        int y = markedPoint.y;
        Point p = new Point(x, y);

        for(int i = 0; i < 8; i++) {
            p.x--;
            p.y++;
            if(p.x >= 0 && p.y < 8) {
                if (boardMap.get(p) == null) {
                    points.add(new Point(p.x, p.y));
                } else if (boardMap.get(p).getColor() != pieceToMove.getColor()) {
                    points.add(new Point(p.x, p.y));
                    break;
                } else {
                    break;
                }
            } else {
                break;
            }
        }
        return points;
    }
}
