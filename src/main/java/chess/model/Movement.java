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

    public void setBoardMap(Map<Point, Piece> boardMap) {
        this.boardMap = boardMap;
    }

    public List<Point> pieceMoveDelegation(Piece pieceToMove, Point markedPoint) {
        points.clear();
        switch (pieceToMove.getPieceType()) {
            case ROOK -> legalMovesRook(pieceToMove, markedPoint);

            case BISHOP -> legalMovesBishop(pieceToMove, markedPoint);

            case KNIGHT -> legalMovesKnight(pieceToMove, markedPoint);

            case QUEEN -> legalMovesQueen(pieceToMove, markedPoint);

            case KING -> legalMovesKing(pieceToMove, markedPoint);

            case PAWN -> legalMovesPawn(pieceToMove, markedPoint);
        }
        return new ArrayList<>(points);
    }

    private void legalMovesPawn(Piece pieceToMove, Point markedPoint) {
        int x = markedPoint.x;
        int y = markedPoint.y;

        if (pieceToMove.getColor() == WHITE){

            if(isUnoccupied(new Point(x, y-1))) {
                addPoint(new Point(x, y-1), pieceToMove);
                //Hardcoded based on standard pawn positions atm. Could use Plies to check if has moved instead
                if(isUnoccupied(new Point(x, y-2)) && markedPoint.y == 6) {
                    addPoint(new Point(x, y-2), pieceToMove);
                }
            }

            if(!isUnoccupied(new Point(x+1,y-1))) addPoint(new Point(x+1,y-1), pieceToMove);
            if(!isUnoccupied(new Point(x-1,y-1))) addPoint(new Point(x-1,y-1), pieceToMove);

        } else if (pieceToMove.getColor() == BLACK){

            if(isUnoccupied(new Point(x, y+1))){
                addPoint(new Point(x, y+1), pieceToMove);
                //Hardcoded based on standard pawn positions atm. Could use Plies to check if has moved instead
                if(isUnoccupied(new Point(x, y+2)) && markedPoint.y == 1) {
                    addPoint(new Point(x, y+2), pieceToMove);
                }
            }

            if(!isUnoccupied(new Point(x+1,y+1))) addPoint(new Point(x+1,y+1), pieceToMove);
            if(!isUnoccupied(new Point(x-1,y+1))) addPoint(new Point(x-1,y+1), pieceToMove);
        }
    }

    private void legalMovesRook(Piece pieceToMove, Point markedPoint) {
        up(pieceToMove, markedPoint,7);
        down(pieceToMove, markedPoint,7);
        left(pieceToMove, markedPoint,7);
        right(pieceToMove, markedPoint,7);
    }

    private void legalMovesBishop(Piece pieceToMove, Point markedPoint) {
        upLeft(pieceToMove, markedPoint,7);
        upRight(pieceToMove, markedPoint,7);
        downRight(pieceToMove, markedPoint,7);
        downLeft(pieceToMove, markedPoint,7);
    }

    private void legalMovesKnight(Piece pieceToMove, Point markedPoint) {
        int x = markedPoint.x;
        int y = markedPoint.y;

        addPoint(new Point(x+1, y-2),pieceToMove);
        addPoint(new Point(x+2, y-1),pieceToMove);
        addPoint(new Point(x+2, y+1),pieceToMove);
        addPoint(new Point(x+1, y+2),pieceToMove);
        addPoint(new Point(x-1, y+2),pieceToMove);
        addPoint(new Point(x-2, y+1),pieceToMove);
        addPoint(new Point(x-2, y-1),pieceToMove);
        addPoint(new Point(x-1, y-2),pieceToMove);
    }

    private void legalMovesKing(Piece pieceToMove, Point markedPoint) {
        up(pieceToMove,markedPoint,1);
        right(pieceToMove,markedPoint,1);
        down(pieceToMove,markedPoint,1);
        left(pieceToMove,markedPoint,1);

        upLeft(pieceToMove,markedPoint,1);
        upRight(pieceToMove,markedPoint,1);
        downLeft(pieceToMove,markedPoint,1);
        downRight(pieceToMove,markedPoint,1);
    }

    public void legalMovesQueen(Piece pieceToMove, Point markedPoint) {
        up(pieceToMove, markedPoint,7);
        down(pieceToMove, markedPoint,7);
        left(pieceToMove, markedPoint,7);
        right(pieceToMove, markedPoint,7);

        upLeft(pieceToMove, markedPoint,7);
        upRight(pieceToMove, markedPoint,7);
        downRight(pieceToMove, markedPoint,7);
        downLeft(pieceToMove, markedPoint,7);
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
            if(addPoint(p,pieceToMove))break;
        }
    }

    private void upRight(Piece pieceToMove, Point markedPoint,int iterations){
        Point p = new Point(markedPoint.x, markedPoint.y);
        for(int i = 0; i < 8 && iterations > 0; i++,iterations--) {
            p.x++;
            p.y--;
            if(addPoint(p,pieceToMove))break;
        }
    }

    private void downRight(Piece pieceToMove, Point markedPoint,int iterations){
        Point p = new Point(markedPoint.x, markedPoint.y);
        for(int i = 0; i < 8 && iterations > 0; i++,iterations--) {
            p.x++;
            p.y++;
            if(addPoint(p,pieceToMove))break;
        }
    }

    private void downLeft(Piece pieceToMove, Point markedPoint, int iterations){
        Point p = new Point(markedPoint.x, markedPoint.y);

        for(int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            p.x--;
            p.y++;
            if(addPoint(p,pieceToMove))break;
        }
    }

    /**
     * Adds point to the list of legal moves if the point is inside the board AND:
     *   - the point is empty
     *   - the point has a piece of the opposite color
     * @param p point to move to
     * @param pieceToMove
     * @return returns boolean that breaks the loop where the method was called, if a point has been added
     */
    private boolean addPoint(Point p, Piece pieceToMove){
        boolean breakLoop;      //Used just to be extra clear, instead of return false or true
        if(p.x >= 0 && p.x < 8 && p.y >= 0 && p.y < 8) {
            if (boardMap.get(p) == null) {
                points.add(new Point(p.x, p.y));
                breakLoop = false;
            } else if (boardMap.get(p).getColor() != pieceToMove.getColor()) {
                points.add(new Point(p.x, p.y));
                breakLoop = true;
            } else {
                breakLoop = true;
            }
        }   else {
            breakLoop = true;
        }
        return breakLoop;
    }
}
