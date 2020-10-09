package chess;

public interface GameObserver {
    void drawPieces();
    void drawDeadPieces();
    void drawLegalMoves();
    void switchedPlayer();
    void pawnPromotion();
    void checkEndGame(String result);
}
