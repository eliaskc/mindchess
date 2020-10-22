package mindchess.model.pieces;

import mindchess.model.enums.ChessColor;
import mindchess.model.enums.PieceType;
import mindchess.model.moveDelegates.*;

import static mindchess.model.enums.PieceType.*;

/**
 * Factory to create Pieces.
 * <p>
 * Creates a different piece depending on what PieceType and Color it gets.
 */
public class PieceFactory {

    public static IPiece createPiece(PieceType pieceType, ChessColor chessColor) throws IllegalArgumentException {
        switch (pieceType) {
            case ROOK -> {
                return createRook(chessColor);
            }

            case KNIGHT -> {
                return createKnight(chessColor);
            }

            case BISHOP -> {
                return createBishop(chessColor);
            }

            case QUEEN -> {
                return createQueen(chessColor);
            }

            case PAWN -> {
                return createPawn(chessColor);
            }

            case KING -> {
                return createKing(chessColor);
            }
        }
        throw new IllegalArgumentException();
    }

    public static IPiece createRook(ChessColor chessColor) {
        return new Piece(chessColor, ROOK, new RookMoveDelegate(), 5);
    }

    public static IPiece createKnight(ChessColor chessColor) {
        return new Piece(chessColor, KNIGHT, new KnightMoveDelegate(), 3);
    }

    public static IPiece createBishop(ChessColor chessColor) {
        return new Piece(chessColor, BISHOP, new BishopMoveDelegate(), 3);
    }

    public static IPiece createQueen(ChessColor chessColor) {
        return new Piece(chessColor, QUEEN, new QueenMoveDelegate(), 9);
    }

    public static IPiece createKing(ChessColor chessColor) {
        return new Piece(chessColor, KING, new KingMoveDelegate(), 100);
    }

    public static IPiece createPawn(ChessColor chessColor) {
        return new Piece(chessColor, PAWN, new PawnMoveDelegate(), 1);
    }
}
