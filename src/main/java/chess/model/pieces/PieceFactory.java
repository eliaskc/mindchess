package chess.model.pieces;

import chess.model.ChessColor;
import chess.model.PieceType;
import chess.model.moveDelegates.*;

import static chess.model.PieceType.*;

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
        return new Piece(chessColor, ROOK, new RookMoveDelegate());
    }

    public static IPiece createKnight(ChessColor chessColor) {
        return new Piece(chessColor, KNIGHT, new KnightMoveDelegate());
    }

    public static IPiece createBishop(ChessColor chessColor) {
        return new Piece(chessColor, BISHOP, new BishopMoveDelegate());
    }

    public static IPiece createQueen(ChessColor chessColor) {
        return new Piece(chessColor, QUEEN, new QueenMoveDelegate());
    }

    public static IPiece createKing(ChessColor chessColor) {
        return new Piece(chessColor, KING, new KingMoveDelegate());
    }

    public static IPiece createPawn(ChessColor chessColor) {
        return new Piece(chessColor, PAWN, new PawnMoveDelegate());
    }
}
