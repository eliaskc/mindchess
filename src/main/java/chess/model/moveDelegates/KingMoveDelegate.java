package chess.model.moveDelegates;

import chess.model.Board;
import chess.model.Square;
import chess.model.SquareType;
import chess.model.util.MovementLogicUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static chess.model.SquareType.CASTLING;

public class KingMoveDelegate implements IMoveDelegate {

    @Override
    public List<Square> fetchMoves(Board board, Square squareToCheck, boolean pieceOnSquareHasMoved) {
        var legalSquares = new ArrayList<Square>();

        legalSquares.addAll(MovementLogicUtil.up(board, squareToCheck, 1));
        legalSquares.addAll(MovementLogicUtil.right(board, squareToCheck, 1));
        legalSquares.addAll(MovementLogicUtil.down(board, squareToCheck, 1));
        legalSquares.addAll(MovementLogicUtil.left(board, squareToCheck, 1));

        legalSquares.addAll(MovementLogicUtil.upLeft(board, squareToCheck, 1));
        legalSquares.addAll(MovementLogicUtil.upRight(board, squareToCheck, 1));
        legalSquares.addAll(MovementLogicUtil.downRight(board, squareToCheck, 1));
        legalSquares.addAll(MovementLogicUtil.downLeft(board, squareToCheck, 1));

        legalSquares.addAll(getCastlingSquares(board, squareToCheck, pieceOnSquareHasMoved));

        return legalSquares;
    }

    List<Square> getCastlingSquares(Board board, Square squareToCheck, boolean hasMoved) {
        List<Square> castlingSquares = new ArrayList<>();

        if (!hasMoved) {
            if (checkRightCastling(board, squareToCheck)) {
                castlingSquares.add(new Square(squareToCheck.getX() + 2, squareToCheck.getY(), CASTLING));
            }
            if (checkLeftCastling(board, squareToCheck)) {
                castlingSquares.add(new Square(squareToCheck.getX() - 2, squareToCheck.getY(), CASTLING));
            }
        }
        return castlingSquares;
    }

    /**
     *
     * @param board
     * @param squareToCheck
     * @return
     */
    private boolean checkRightCastling(Board board, Square squareToCheck) {
        for (int i = squareToCheck.getX() + 1; i <= squareToCheck.getX() + 2; i++) {
            if (MovementLogicUtil.isOccupied(board, new Square(i, squareToCheck.getY()))) {
                return false;
            }
        }
        Square s = new Square(squareToCheck.getX() + 3, squareToCheck.getY());
        if (MovementLogicUtil.isOccupied(board, s)) {
            return board.isPieceOnSquareRook(s) && board.pieceOnSquareColorEquals(s, board.fetchPieceOnSquareColor(squareToCheck));
        }
        return false;
    }

    /**
     *
     * @param board
     * @param squareToCheck
     * @return
     */
    private boolean checkLeftCastling(Board board, Square squareToCheck) {
        for (int i = squareToCheck.getX() - 1; i >= squareToCheck.getX() - 3; i--) {
            if (MovementLogicUtil.isOccupied(board, new Square(i, squareToCheck.getY()))) {
                return false;
            }
        }
        Square s = new Square(squareToCheck.getX() - 4, squareToCheck.getY());
        if (MovementLogicUtil.isOccupied(board, s)) {
            return board.isPieceOnSquareRook(s) && board.pieceOnSquareColorEquals(s, board.fetchPieceOnSquareColor(squareToCheck));
        }
        return false;
    }
}
