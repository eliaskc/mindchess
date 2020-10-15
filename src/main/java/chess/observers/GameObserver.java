package chess.observers;

import chess.model.ChessColor;

import java.awt.*;

public interface GameObserver {
    void drawPieces();
    void drawDeadPieces();
    void drawLegalMoves();
    void switchedPlayer();
    void updateTimer();
    void pawnPromotionSetup(ChessColor chessColor);
}
