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
    private List<Point> points = new ArrayList<>(); // Holds points which are valid to move to

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
        
        up(pieceToMove, markedPoint,7);
        down(pieceToMove, markedPoint,7);
        left(pieceToMove, markedPoint,7);
        right(pieceToMove, markedPoint,7);
        
        return points;
    }

    private List<Point> legalMovesBishop(Piece pieceToMove, Point markedPoint) {
        points.clear();

        upLeft(pieceToMove, markedPoint,7);
        upRight(pieceToMove, markedPoint,7);
        downRight(pieceToMove, markedPoint,7);
        downLeft(pieceToMove, markedPoint,7);

        return points;
    }

    private List<Point> legalMovesKnight(Piece pieceToMove, Point markedPoint) {
        points.clear();

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
        points.clear();
        //Check if the pawn can move up, if they are at their starting position they can move two squares, the pawn can't take a piece on front of it
        if(markedPoint.getY() == 6){
            if(isUnoccupied(new Point(markedPoint.x,markedPoint.y-1)) && isUnoccupied(new Point(markedPoint.x,markedPoint.y-2)) ){
                up(pieceToMove,markedPoint,2);
            }else if(isUnoccupied(new Point(markedPoint.x,markedPoint.y-1))) {
                up(pieceToMove,markedPoint,1);
            }
        } else{
            if(isUnoccupied(new Point(markedPoint.x,markedPoint.y-1))) up(pieceToMove,markedPoint,1);
        }

        //if there are a opponent in its diagonal up squares it can take it
        if(isUnoccupied(new Point(markedPoint.x-1,markedPoint.y-1)) && boardMap.get(new Point(markedPoint.x-1,markedPoint.y-1)).getColor() == BLACK){
            upRight(pieceToMove,markedPoint,1);
        }
        if(!isUnoccupied(new Point(markedPoint.x+1,markedPoint.y-1)) && boardMap.get(new Point(markedPoint.x+1,markedPoint.y-1)).getColor() == BLACK){
            upLeft(pieceToMove,markedPoint,1);
        }

        return points;
    }

    private List<Point> legalMovesBlackPawn(Piece pieceToMove, Point markedPoint) {
        points.clear();

        //Check if the pawn can move down, if they are at their starting position they can move two squares, the pawn can't take a piece on front of it
        if(markedPoint.getY() == 1){
            if(isUnoccupied(new Point(markedPoint.x,markedPoint.y+1)) && boardMap.get(new Point(markedPoint.x,markedPoint.y+2)) == null ){
                down(pieceToMove,markedPoint,2);
            }else if(isUnoccupied(new Point(markedPoint.x,markedPoint.y+1))) {
                down(pieceToMove,markedPoint,1);
            }
        } else{
            if(isUnoccupied(new Point(markedPoint.x,markedPoint.y+1))) down(pieceToMove,markedPoint,1);
        }

        //if there are a opponent in its diagonal down squares it can take it
        if(!isUnoccupied(new Point(markedPoint.x+1,markedPoint.y+1)) && boardMap.get(new Point(markedPoint.x+1,markedPoint.y+1)).getColor() == WHITE){
            downRight(pieceToMove,markedPoint,1);
        }
        if(!isUnoccupied(new Point(markedPoint.x-1,markedPoint.y+1)) && boardMap.get(new Point(markedPoint.x-1,markedPoint.y+1)).getColor() == WHITE){
            downLeft(pieceToMove,markedPoint,1);
        }

        return points;
    }

    private List<Point> legalMovesKing(Piece pieceToMove, Point markedPoint) {
        points.clear();

        up(pieceToMove,markedPoint,1);
        right(pieceToMove,markedPoint,1);
        down(pieceToMove,markedPoint,1);
        left(pieceToMove,markedPoint,1);

        upLeft(pieceToMove,markedPoint,1);
        upRight(pieceToMove,markedPoint,1);
        downLeft(pieceToMove,markedPoint,1);
        downRight(pieceToMove,markedPoint,1);

        return points;
    }

    public List<Point> legalMovesQueen(Piece pieceToMove, Point markedPoint) {
        points.clear();

        up(pieceToMove, markedPoint,7);
        down(pieceToMove, markedPoint,7);
        left(pieceToMove, markedPoint,7);
        right(pieceToMove, markedPoint,7);

        upLeft(pieceToMove, markedPoint,7);
        upRight(pieceToMove, markedPoint,7);
        downRight(pieceToMove, markedPoint,7);
        downLeft(pieceToMove, markedPoint,7);
        return points;
    }

    private boolean isUnoccupied(Point p){
        if(boardMap.get(p) == null) return true;
        return false;
    }

    private void up(Piece pieceToMove, Point markedPoint, int iterations){
        for(int i = markedPoint.y - 1; i >= 0 && iterations > 0; i--, iterations--) {
            Point p = new Point(markedPoint.x, i);
            if(addPoint(p,pieceToMove))break;
        }
    }

    private void down(Piece pieceToMove, Point markedPoint, int iterations){
        for(int i = markedPoint.y + 1; i < 8 && iterations > 0; i++, iterations--) {
            Point p = new Point(markedPoint.x, i);
            if(addPoint(p,pieceToMove))break;
        }
    }

    private void left(Piece pieceToMove, Point markedPoint, int iterations){
        for(int i = markedPoint.x - 1; i >= 0 && iterations > 0; i--,iterations--) {
            Point p = new Point(i, markedPoint.y);
            if(addPoint(p,pieceToMove))break;
        }
    }

    private void right(Piece pieceToMove, Point markedPoint,int iterations){
        for(int i = markedPoint.x + 1; i < 8 && iterations > 0; i++, iterations--) {
            Point p = new Point(i, markedPoint.y);
            if(addPoint(p,pieceToMove))break;
        }
    }

    private void upLeft(Piece pieceToMove, Point markedPoint, int iterations){
        Point p = new Point(markedPoint.x, markedPoint.y);
        for(int i = 0; i < 8 && iterations > 0; i++,iterations--) {
            p.x--;
            p.y--;
            if(p.x >= 0 && p.y >= 0) {
                if(addPoint(p,pieceToMove))break;
            } else {
                break;
            }
        }
    }

    private void upRight(Piece pieceToMove, Point markedPoint,int iterations){
        Point p = new Point(markedPoint.x, markedPoint.y);
        for(int i = 0; i < 8 && iterations > 0; i++,iterations--) {
            p.x++;
            p.y--;
            if(p.x < 8 && p.y >= 0) {
                if(addPoint(p,pieceToMove))break;
            } else {
                break;
            }
        }
    }

    private void downRight(Piece pieceToMove, Point markedPoint,int iterations){
        Point p = new Point(markedPoint.x, markedPoint.y);
        for(int i = 0; i < 8 && iterations > 0; i++,iterations--) {
            p.x++;
            p.y++;
            if(p.x < 8 && p.y < 8) {
                if(addPoint(p,pieceToMove))break;
            } else {
                break;
            }
        }
    }

    private void downLeft(Piece pieceToMove, Point markedPoint, int iterations){
        Point p = new Point(markedPoint.x, markedPoint.y);

        for(int i = 0; i < 8 && iterations > 0; i++, iterations--) {
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
