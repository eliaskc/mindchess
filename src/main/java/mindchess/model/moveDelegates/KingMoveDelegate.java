package mindchess.model.moveDelegates;

import mindchess.model.enums.ChessColor;
import mindchess.model.*;

import java.util.ArrayList;
import java.util.List;

import static mindchess.model.enums.ChessColor.BLACK;
import static mindchess.model.enums.ChessColor.WHITE;
import static mindchess.model.enums.SquareType.CASTLING;

public class KingMoveDelegate implements IMoveDelegate {

    @Override
    public List<Square> fetchMoves(IBoard board, Square squareToCheck, boolean pieceOnSquareHasMoved, boolean checkKingSuicide) {
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
            legalSquares.removeIf(opponentLegalSquares::contains);
            if (MovementLogicUtil.isKingInCheck(board, squareToCheck, opponentColor))
                legalSquares.removeIf(p -> p.getSquareType() == CASTLING);
        }


        return legalSquares;
    }

    List<Square> getCastlingSquares(IBoard board, Square squareToCheck, boolean hasMoved) {
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
