package chess.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import static chess.model.Color.*;

/**
 * Is responisble for finding legal moves
 */
public class Movement {
    private Map<Point, Piece> boardMap = new HashMap<>();
    private List<Point> points = new ArrayList<>();

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
                if (pieceToMove.getColor() == WHITE) {
                    legalMoves.addAll(legalMovesWhitePawn(pieceToMove, markedPoint));
                } else if (pieceToMove.getColor() == BLACK) {
                    legalMoves.addAll(legalMovesBlackPawn(pieceToMove, markedPoint));
                }
                break;
        }
        return legalMoves;
    }

    private List<Point> legalMovesRook(Piece pieceToMove, Point markedPoint) {
        points.clear();
        
        up(pieceToMove, markedPoint);
        down(pieceToMove, markedPoint);
        left(pieceToMove, markedPoint);
        right(pieceToMove, markedPoint);
        
        return points;
    }

    private List<Point> legalMovesBishop(Piece pieceToMove, Point markedPoint) {
        points.clear();

        upLeft(pieceToMove, markedPoint);
        upRight(pieceToMove, markedPoint);
        downRight(pieceToMove, markedPoint);
        downLeft(pieceToMove, markedPoint);

        return points;
    }

    private List<Point> legalMovesKnight(Piece pieceToMove, Point markedPoint) {
        List<Point> points = new ArrayList<>();

        int x = markedPoint.x;
        int y = markedPoint.y;

        points.add(new Point(x+1, y-2));
        points.add(new Point(x+2, y-1));
        points.add(new Point(x+2, y+1));
        points.add(new Point(x+1, y+2));
        points.add(new Point(x-1, y+2));
        points.add(new Point(x-2, y+1));
        points.add(new Point(x-2, y-1));
        points.add(new Point(x-1, y-2));

        //Ohhh my
        points.removeIf(p -> p.x > 7 || p.x < 0 || p.y > 7 || p.y < 0 );
        points.removeIf(p -> boardMap.get(p) != null && boardMap.get(p).getColor() == pieceToMove.getColor());


        return points;
    }

    private List<Point> legalMovesWhitePawn(Piece pieceToMove, Point markedPoint) {
        List<Point> pawnPoints = new ArrayList<>();
        points.clear();
        //Need to check if the pawn has moved already, because if not then it can move 2 steps

        up(pieceToMove, markedPoint);
        if (points.size() != 0 && boardMap.get(points.get(0)) == null) pawnPoints.add(points.get(0));
        points.clear();
        //Check if the square to the right and up has a black piece on it, and if so add it to
        
        //the list of legal points
        upRight(pieceToMove, markedPoint);
        if(points.size() != 0 && boardMap.get(points.get(0)) != null) {
            if (boardMap.get(points.get(0)).getColor() == BLACK) {
                pawnPoints.add(points.get(0));
            }
        }
        points.clear();

        //same for left and up
        upLeft(pieceToMove, markedPoint);
        if(points.size() != 0 && boardMap.get(points.get(0)) != null) {
            if (boardMap.get(points.get(0)).getColor() == BLACK) {
                pawnPoints.add(points.get(0));
            }
        }

        return pawnPoints;
    }

    private List<Point> legalMovesBlackPawn(Piece pieceToMove, Point markedPoint) {
        List<Point> pawnPoints = new ArrayList<>();
        points.clear();
        //Need to check if the pawn has moved already, because if not then it can move 2 steps

        down(pieceToMove, markedPoint);
        if (points.size() != 0 && boardMap.get(points.get(0)) == null) pawnPoints.add(points.get(0));
        points.clear();

        downRight(pieceToMove, markedPoint);
        if(points.size() != 0 && boardMap.get(points.get(0)) != null) {
            if (boardMap.get(points.get(0)).getColor() == WHITE) {
                pawnPoints.add(points.get(0));
            }
        }
        points.clear();

        downLeft(pieceToMove, markedPoint);
        if(points.size() != 0 && boardMap.get(points.get(0)) != null) {
            if (boardMap.get(points.get(0)).getColor() == WHITE) {
                pawnPoints.add(points.get(0));
            }
        }

        return pawnPoints;
    }

    private List<Point> legalMovesKing(Piece pieceToMove, Point markedPoint) {
        points.clear();

        /*
        if (up(pieceToMove, markedPoint).size() != 0) points.add(up(pieceToMove, markedPoint).get(0));
        if (right(pieceToMove, markedPoint).size() != 0) points.add(right(pieceToMove, markedPoint).get(0));
        if (down(pieceToMove, markedPoint).size() != 0) points.add(down(pieceToMove, markedPoint).get(0));
        if (left(pieceToMove, markedPoint).size() != 0) points.add(left(pieceToMove, markedPoint).get(0));

        if (upLeft(pieceToMove, markedPoint).size() != 0) points.add(upLeft(pieceToMove, markedPoint).get(0));
        if (upRight(pieceToMove, markedPoint).size() != 0) points.add(upRight(pieceToMove, markedPoint).get(0));
        if (downRight(pieceToMove, markedPoint).size() != 0) points.add(downRight(pieceToMove, markedPoint).get(0));
        if (downLeft(pieceToMove, markedPoint).size() != 0) points.add(downLeft(pieceToMove, markedPoint).get(0));
         */

        return points;
    }

    public List<Point> legalMovesQueen(Piece pieceToMove, Point markedPoint) {
        points.clear();
        up(pieceToMove, markedPoint);
        down(pieceToMove, markedPoint);
        left(pieceToMove, markedPoint);
        right(pieceToMove, markedPoint);

        upLeft(pieceToMove, markedPoint);
        upRight(pieceToMove, markedPoint);
        downRight(pieceToMove, markedPoint);
        downLeft(pieceToMove, markedPoint);
        return points;
    }

    private void up(Piece pieceToMove, Point markedPoint){
        for(int i = markedPoint.y - 1; i >= 0; i--) {
            Point p = new Point(markedPoint.x, i);
            if(addPoint(p,pieceToMove))break;
        }
    }

    private void up(Piece pieceToMove, Point markedPoint, PieceType pieceType){
        if(pieceType == PieceType.PAWN || pieceType == PieceType.KING){
            addPoint(new Point(markedPoint.x,markedPoint.y-1),pieceToMove);
        } else {
            up(pieceToMove,markedPoint);
        }
    }

    private void down(Piece pieceToMove, Point markedPoint){

        for(int i = markedPoint.y + 1; i < 8; i++) {
            Point p = new Point(markedPoint.x, i);
            if(addPoint(p,pieceToMove))break;
        }
    }

    private void down(Piece pieceToMove, Point markedPoint,PieceType pieceType){
        if(pieceType == PieceType.PAWN || pieceType == PieceType.KING){
            addPoint(new Point(markedPoint.x,markedPoint.y-1),pieceToMove);
        } else {
            up(pieceToMove,markedPoint);
        }

    }

    private void left(Piece pieceToMove, Point markedPoint){

        for(int i = markedPoint.x - 1; i >= 0; i--) {
            Point p = new Point(i, markedPoint.y);
            if(addPoint(p,pieceToMove))break;
        }
    }

    private void right(Piece pieceToMove, Point markedPoint){

        for(int i = markedPoint.x + 1; i < 8; i++) {
            Point p = new Point(i, markedPoint.y);
            if(addPoint(p,pieceToMove))break;
        }
    }

    private void upLeft(Piece pieceToMove, Point markedPoint){

        Point p = new Point(markedPoint.x, markedPoint.y);

        for(int i = 0; i < 8; i++) {
            p.x--;
            p.y--;
            if(p.x >= 0 && p.y >= 0) {
                if(addPoint(p,pieceToMove))break;
            } else {
                break;
            }
        }
    }

    private void upRight(Piece pieceToMove, Point markedPoint){

        Point p = new Point(markedPoint.x, markedPoint.y);

        for(int i = 0; i < 8; i++) {
            p.x++;
            p.y--;
            if(p.x < 8 && p.y >= 0) {
                if(addPoint(p,pieceToMove))break;
            } else {
                break;
            }
        }
    }

    private void downRight(Piece pieceToMove, Point markedPoint){

        Point p = new Point(markedPoint.x, markedPoint.y);

        for(int i = 0; i < 8; i++) {
            p.x++;
            p.y++;
            if(p.x < 8 && p.y < 8) {
                if(addPoint(p,pieceToMove))break;
            } else {
                break;
            }
        }
    }

    private void downLeft(Piece pieceToMove, Point markedPoint){

        Point p = new Point(markedPoint.x, markedPoint.y);

        for(int i = 0; i < 8; i++) {
            p.x--;
            p.y++;
            if(p.x >= 0 && p.y < 8) {
                if(addPoint(p,pieceToMove))break;
            } else {
                break;
            }
        }
    }


    private boolean addPoint(Point p, Piece pieceToMove){
        boolean breakLoop;      //Used just to be extra clear, instead of return false or true
        if (boardMap.get(p) == null) {
            points.add(new Point(p.x, p.y));
            breakLoop = false;
        } else if (boardMap.get(p).getColor() != pieceToMove.getColor()) {
            points.add(new Point(p.x, p.y));
            breakLoop = true;
        } else {
            breakLoop = true;
        }
        return breakLoop;
    }
}
