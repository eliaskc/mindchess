package chess.model;

import chess.model.pieces.*;

public class PieceFactory {
    static Pawn createPawn(Square position, boolean isActive, Color color) {
        Pawn pawn = new Pawn(position, isActive, color);
        pawn.fetchImage();
        return pawn;
    }

    static King createKing(Square position, boolean isActive, Color color) {
        King king = new King(position, isActive, color);
        king.fetchImage();
        return king;
    }

    static Queen createQueen(Square position, boolean isActive, Color color) {
        Queen queen = new Queen(position, isActive, color);
        queen.fetchImage();
        return queen;
    }

    static Bishop createBishop(Square position, boolean isActive, Color color) {
        Bishop bishop = new Bishop(position, isActive, color);
        bishop.fetchImage();
        return bishop;
    }

    static Rook createRook(Square position, boolean isActive, Color color) {
        Rook rook = new Rook(position, isActive, color);
        rook.fetchImage();
        return rook;
    }

    static Knight createKnight(Square position, boolean isActive, Color color) {
        Knight knight = new Knight(position, isActive, color);
        knight.fetchImage();
        return knight;
    }
}
