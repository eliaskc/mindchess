package mindchess.model.moveDelegates;

import mindchess.model.Board;
import mindchess.model.ChessColor;
import mindchess.model.MovementLogicUtil;
import mindchess.model.Square;

import java.util.ArrayList;
import java.util.List;

import static mindchess.model.ChessColor.BLACK;
import static mindchess.model.ChessColor.WHITE;
import static mindchess.model.SquareType.CASTLING;

public class KingMoveDelegate implements IMoveDelegate {

    @Override
    public List<Square> fetchMoves(Board board, Square squareToCheck, boolean pieceOnSquareHasMoved, boolean checkKingSuicide) {
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

        if (checkKingSuicide) {
            ChessColor pieceToMoveColor = MovementLogicUtil.fetchPieceColorOnSquare(board, squareToCheck);
            ChessColor opponentColor = (pieceToMoveColor == WHITE) ? BLACK : WHITE;
            List<Square> opponentLegalSquares = MovementLogicUtil.fetchLegalSquaresByColor(board, opponentColor);
            legalSquares.removeIf(p -> opponentLegalSquares.contains(p));
        }
        return legalSquares;
    }

    List<Square> getCastlingSquares(Board board, Square squareToCheck, boolean hasMoved) {
        List<Square> castlingSquares = new ArrayList<>();

        if (!hasMoved) {
            if (MovementLogicUtil.checkRightCastling(board, squareToCheck)) {
                castlingSquares.add(new Square(squareToCheck.getX() + 2, squareToCheck.getY(), CASTLING));
            }
            if (MovementLogicUtil.checkLeftCastling(board, squareToCheck)) {
                castlingSquares.add(new Square(squareToCheck.getX() - 2, squareToCheck.getY(), CASTLING));
            }
        }
        return castlingSquares;
    }
}
