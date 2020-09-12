package chess.model;

import chess.model.pieces.*;

public class PieceFactory {
    public static Pawn createPawn(boolean isActive, Color color) {
        return new Pawn(isActive, color);
    }

    private static King createKing(Square position, boolean isActive, Color color) {
        return new King(position, isActive, color);
    }

    private static Queen createQueen(Square position, boolean isActive, Color color) {
        return new Queen(position, isActive, color);
    }

    private static Bishop createBishop(Square position, boolean isActive, Color color) {
        return new Bishop(position, isActive, color);
    }

    private static Rook createRook(Square position, boolean isActive, Color color) {
        return new Rook(position, isActive, color);
    }

    private static Knight createKnight(Square position, boolean isActive, Color color) {
        return new Knight(position, isActive, color);
    }
}
