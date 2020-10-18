package chess.model.pieces;

import chess.model.ChessColor;

public class PieceFactory {

    public static IPiece createPiece(String pieceName, ChessColor chessColor) throws IllegalArgumentException {
        switch (pieceName) {
            case "Rook" -> { return createRook(chessColor); }

            case "Knight" -> { return createKnight(chessColor); }

            case "Bishop" -> { return createBishop(chessColor); }

            case "Queen" -> { return createQueen(chessColor); }

            case "Pawn" -> { return createPawn(chessColor); }

            case "King" -> { return createKing(chessColor); }
        }
        throw new IllegalArgumentException();
    }

    public static IPiece createRook(ChessColor chessColor) {
        return new Rook(chessColor);
    }

    public static IPiece createKnight(ChessColor chessColor) {
        return new Knight(chessColor);
    }

    public static IPiece createBishop(ChessColor chessColor) {
        return new Bishop(chessColor);
    }

    public static IPiece createQueen(ChessColor chessColor) {
        return new Queen(chessColor);
    }

    public static IPiece createKing(ChessColor chessColor) {
        return new King(chessColor);
    }

    public static IPiece createPawn(ChessColor chessColor) {
        return new Pawn(chessColor);
    }
}
