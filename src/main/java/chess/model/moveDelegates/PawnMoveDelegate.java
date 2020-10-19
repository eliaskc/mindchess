package chess.model.moveDelegates;

import chess.model.Board;
import chess.model.util.MovementLogicUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static chess.model.ChessColor.BLACK;
import static chess.model.ChessColor.WHITE;

public class PawnMoveDelegate implements IMoveDelegate {
    
    @Override
    public List<Point> fetchMoves(Board board, Point pointToCheck, boolean pieceOnPointHasMoved) {
        var legalPoints = new ArrayList<Point>();

        if (board.pieceOnPointColorEquals(pointToCheck, WHITE)) {
            legalPoints.addAll(fetchMovesWhitePawn(board, pointToCheck, pieceOnPointHasMoved));
        } else if (board.pieceOnPointColorEquals(pointToCheck, BLACK)) {
            legalPoints.addAll(fetchMovesBlackPawn(board, pointToCheck, pieceOnPointHasMoved));
        }

        return legalPoints;
    }
    
    private List<Point> fetchMovesWhitePawn(Board board, Point pointToCheck, boolean pieceOnPointHasMoved) {
        var returnList = new ArrayList<Point>();
        int x = pointToCheck.x;
        int y = pointToCheck.y;
        
        if (!MovementLogicUtil.isOccupied(board, new Point(x, y - 1))) {
            returnList.addAll(MovementLogicUtil.up(board, pointToCheck, 1));

            if (!MovementLogicUtil.isOccupied(board, new Point(x, y - 2)) && !pieceOnPointHasMoved) {
                MovementLogicUtil.addPointIfLegal(board, new Point(x, y - 2), pointToCheck, returnList);
            }
        }
        if (MovementLogicUtil.isOccupied(board, new Point(x + 1, y - 1))) returnList.addAll(MovementLogicUtil.upRight(board, pointToCheck, 1));
        if (MovementLogicUtil.isOccupied(board, new Point(x - 1, y - 1))) returnList.addAll(MovementLogicUtil.upLeft(board, pointToCheck, 1));

        return returnList;
    }
    
    private List<Point> fetchMovesBlackPawn(Board board, Point pointToCheck, boolean pieceOnPointHasMoved) {
        var returnList = new ArrayList<Point>();
        int x = pointToCheck.x;
        int y = pointToCheck.y;

        if (!MovementLogicUtil.isOccupied(board,new Point(x, y + 1))) {
            returnList.addAll(MovementLogicUtil.down(board, pointToCheck, 1));

            if (!MovementLogicUtil.isOccupied(board, new Point(x, y + 2)) && !pieceOnPointHasMoved) {
                MovementLogicUtil.addPointIfLegal(board, new Point(x, y + 2), pointToCheck, returnList);
            }
        }
        if (MovementLogicUtil.isOccupied(board, new Point(x + 1, y + 1))) returnList.addAll(MovementLogicUtil.downRight(board, pointToCheck, 1));
        if (MovementLogicUtil.isOccupied(board, new Point(x - 1, y + 1))) returnList.addAll(MovementLogicUtil.downLeft(board, pointToCheck, 1));

        return returnList;
    }
}
