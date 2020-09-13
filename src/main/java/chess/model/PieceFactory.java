package chess.model;

import chess.model.pieces.*;

public class PieceFactory {
    static Pawn createPawn(Square position, boolean isActive, Color color) {
        return new Pawn(position, isActive, color);
    }

    static King createKing(Square position, boolean isActive, Color color) {
        return new King(position, isActive, color);
    }

    static Queen createQueen(Square position, boolean isActive, Color color) {
        return new Queen(position, isActive, color);
    }

    static Bishop createBishop(Square position, boolean isActive, Color color) {
        Bishop bishop = new Bishop(position, isActive, color);
        bishop.fetchImage();
        return bishop;
    }

    static Rook createRook(Square position, boolean isActive, Color color) {
        return new Rook(position, isActive, color);
    }

    static Knight createKnight(Square position, boolean isActive, Color color) {
        return new Knight(position, isActive, color);
    }
}
