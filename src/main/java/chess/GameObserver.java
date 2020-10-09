package chess;

import chess.model.ChessColor;

import java.awt.*;

public interface GameObserver {
    void drawPieces();
    void drawDeadPieces();
    void drawLegalMoves();
    void switchedPlayer();
    void pawnPromotionSetup(ChessColor chessColor);
}
