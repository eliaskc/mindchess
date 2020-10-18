package chess.observers;

import chess.model.ChessColor;

public interface GameObserver {
    void drawPieces();
    void drawDeadPieces();
    void drawLegalMoves();
    void switchedPlayer();
    void updateTimer();
    void pawnPromotionSetup(ChessColor chessColor);
    void kingInCheck(int x, int y);
}
