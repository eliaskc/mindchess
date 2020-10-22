package mindchess.observers;

import mindchess.model.enums.ChessColor;

public interface GameObserver {
    void drawPieces();

    void drawDeadPieces();

    void drawLegalMoves();

    void switchedPlayer();

    void updateTimer();

    void pawnPromotionSetup(ChessColor chessColor);

    void kingInCheck(int x, int y);
}
