package chess.model.util;

import chess.model.Board;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Is responsible for finding legal moves
 */
public class MovementLogicUtil {
    private MovementLogicUtil() {
    }

    public static List<Point> up(Board board, Point pointToCheck, int iterations) {
        var returnList = new ArrayList<Point>();
        for (int i = pointToCheck.y - 1; i >= 0 && iterations > 0; i--, iterations--) {
            Point p = new Point(pointToCheck.x, i);
            if (addPointIfLegal(board, p, pointToCheck, returnList)) break;
        }
        return returnList;
    }

    public static List<Point> down(Board board, Point pointToCheck, int iterations) {
        var returnList = new ArrayList<Point>();
        for (int i = pointToCheck.y + 1; i < 8 && iterations > 0; i++, iterations--) {
            Point p = new Point(pointToCheck.x, i);
            if (addPointIfLegal(board, p, pointToCheck, returnList)) break;
        }
        return returnList;
    }

    public static List<Point> left(Board board, Point pointToCheck, int iterations) {
        var returnList = new ArrayList<Point>();
        for (int i = pointToCheck.x - 1; i >= 0 && iterations > 0; i--, iterations--) {
            Point p = new Point(i, pointToCheck.y);
            if (addPointIfLegal(board, p, pointToCheck, returnList)) break;
        }
        return returnList;
    }

    public static List<Point> right(Board board, Point pointToCheck, int iterations) {
        var returnList = new ArrayList<Point>();
        for (int i = pointToCheck.x + 1; i < 8 && iterations > 0; i++, iterations--) {
            Point p = new Point(i, pointToCheck.y);
            if (addPointIfLegal(board, p, pointToCheck, returnList)) break;
        }
        return returnList;
    }

    public static List<Point> upLeft(Board board, Point pointToCheck, int iterations) {
        var returnList = new ArrayList<Point>();
        Point p = new Point(pointToCheck.x, pointToCheck.y);
        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            p.x--;
            p.y--;
            if (addPointIfLegal(board, p, pointToCheck, returnList)) break;
        }
        return returnList;
    }

    public static List<Point> upRight(Board board, Point pointToCheck, int iterations) {
        var returnList = new ArrayList<Point>();
        Point p = new Point(pointToCheck.x, pointToCheck.y);
        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            p.x++;
            p.y--;
            if (addPointIfLegal(board, p, pointToCheck, returnList)) break;
        }
        return returnList;
    }

    public static List<Point> downRight(Board board, Point pointToCheck, int iterations) {
        var returnList = new ArrayList<Point>();
        Point p = new Point(pointToCheck.x, pointToCheck.y);
        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            p.x++;
            p.y++;
            if (addPointIfLegal(board, p, pointToCheck, returnList)) break;
        }
        return returnList;
    }

    public static List<Point> downLeft(Board board, Point pointToCheck, int iterations) {
        var returnList = new ArrayList<Point>();
        Point p = new Point(pointToCheck.x, pointToCheck.y);

        for (int i = 0; i < 8 && iterations > 0; i++, iterations--) {
            p.x--;
            p.y++;
            if (addPointIfLegal(board, p, pointToCheck, returnList)) break;
        }
        return returnList;
    }

    /**
     * Adds point to the list of legal moves if the point is inside the board AND:
     * - the point is empty
     * - the point has a piece of the opposite color
     *
     * @param board
     * @param p            potential legal point
     * @param pointToCheck point moving from
     * @param listToAddTo
     * @return returns boolean that breaks the loop where the method was called, if a point has been added
     */
    public static boolean addPointIfLegal(Board board, Point p, Point pointToCheck, List<Point> listToAddTo) {
        if (p.x >= 0 && p.x < 8 && p.y >= 0 && p.y < 8) {
            if (!isOccupied(board, p)) {
                listToAddTo.add(new Point(p.x, p.y));
                return false;
            } else if (!board.pieceOnPointColorEquals(p, board.fetchPieceOnPointColor(pointToCheck))) {
                listToAddTo.add(new Point(p.x, p.y));
                return true;
            }
        }
        return true;
    }

    public static boolean isOccupied(Board board, Point p) {
        return board.isOccupied(p);
    }

/*    private List<Point> fetchOpponentLegalPoints(ChessColor color){
        List<Point> opponentLegalPoints = new ArrayList<>();
        checkingOpponentLegalPointsInProgress = true;

        for (Map.Entry<Point, Piece> entry : boardMap.entrySet()) {
            if(!(entry.getValue().getColor().equals(color))){
                opponentLegalPoints.addAll(fetchLegalMoves(boardMap.get(entry.getKey()), entry.getKey()));
            }
        }

        checkingOpponentLegalPointsInProgress = false;
        return opponentLegalPoints;
    }*/

    /*static public List<Point> getEnPassantPoints(Ply lastPly, Point pointToCheck){
        List<Point> enPassantPoints = new ArrayList<>();
        if (pieceName.equals("Pawn") && !board.pieceOnPointColorEquals(movedTo, pieceToMoveColor) && Math.abs(movedFrom.y - movedTo.y) == 2) {
            if ((movedTo.x == pointToCheck.x + 1 || movedFrom.x == pointToCheck.x - 1) && movedTo.y == pointToCheck.y) {
                if (board.pieceOnPointColorEquals(movedTo, BLACK)) {
                    enPassantPoints.add(new Point(movedTo.x, movedTo.y - 1));
                } else if (board.pieceOnPointColorEquals(movedTo, WHITE)) {
                    enPassantPoints.add(new Point(movedTo.x, movedTo.y + 1));
                }
                enPassantPossible = true;
            } else {
               enPassantPossible = false;
            }
        }
        return enPassantPoints;
    }*/

}
