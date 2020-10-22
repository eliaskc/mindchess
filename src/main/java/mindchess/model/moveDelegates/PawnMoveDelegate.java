package mindchess.model.moveDelegates;

import mindchess.model.Board;
import mindchess.model.Square;
import mindchess.model.MovementLogicUtil;

import java.util.ArrayList;
import java.util.List;

import static mindchess.model.ChessColor.BLACK;
import static mindchess.model.ChessColor.WHITE;

public class PawnMoveDelegate implements IMoveDelegate {
    
    @Override
    public List<Square> fetchMoves(Board board, Square squareToCheck, boolean pieceOnSquareHasMoved, boolean checkKingSuicide) {
        var legalSquares = new ArrayList<Square>();

        if (board.pieceOnSquareColorEquals(squareToCheck, WHITE)) {
            legalSquares.addAll(fetchMovesWhitePawn(board, squareToCheck, pieceOnSquareHasMoved));
        } else if (board.pieceOnSquareColorEquals(squareToCheck, BLACK)) {
            legalSquares.addAll(fetchMovesBlackPawn(board, squareToCheck, pieceOnSquareHasMoved));
        }

        MovementLogicUtil.checkPawnPromotion(board, legalSquares, squareToCheck);

        return legalSquares;
    }

    private List<Square> fetchMovesWhitePawn(Board board, Square squareToCheck, boolean pieceOnSquareHasMoved) {
        var returnList = new ArrayList<Square>();
        int x = squareToCheck.getX();
        int y = squareToCheck.getY();
        
        if (!MovementLogicUtil.isOccupied(board, new Square(x, y - 1))) {
            returnList.addAll(MovementLogicUtil.up(board, squareToCheck, 1));

            if (!MovementLogicUtil.isOccupied(board, new Square(x, y - 2)) && !pieceOnSquareHasMoved) {
                MovementLogicUtil.addSquareIfLegal(board, new Square(x, y - 2), squareToCheck, returnList);
            }
        }
        if (MovementLogicUtil.isOccupied(board, new Square(x + 1, y - 1))) returnList.addAll(MovementLogicUtil.upRight(board, squareToCheck, 1));
        if (MovementLogicUtil.isOccupied(board, new Square(x - 1, y - 1))) returnList.addAll(MovementLogicUtil.upLeft(board, squareToCheck, 1));

        return returnList;
    }
    
    private List<Square> fetchMovesBlackPawn(Board board, Square squareToCheck, boolean pieceOnSquareHasMoved) {
        var returnList = new ArrayList<Square>();
        int x = squareToCheck.getX();
        int y = squareToCheck.getY();

        if (!MovementLogicUtil.isOccupied(board,new Square(x, y + 1))) {
            returnList.addAll(MovementLogicUtil.down(board, squareToCheck, 1));

            if (!MovementLogicUtil.isOccupied(board, new Square(x, y + 2)) && !pieceOnSquareHasMoved) {
                MovementLogicUtil.addSquareIfLegal(board, new Square(x, y + 2), squareToCheck, returnList);
            }
        }
        if (MovementLogicUtil.isOccupied(board, new Square(x + 1, y + 1))) returnList.addAll(MovementLogicUtil.downRight(board, squareToCheck, 1));
        if (MovementLogicUtil.isOccupied(board, new Square(x - 1, y + 1))) returnList.addAll(MovementLogicUtil.downLeft(board, squareToCheck, 1));

        return returnList;
    }


}
