package mindchess.model;

/**
 * observers used for notifying the game when changes in a state happens
 */

public interface GameStateObserver {
    void notifyDrawLegalMoves();
    void notifySwitchPlayer();
    void notifyDrawPieces();
    void notifyDrawDeadPieces();
    void notifyKingInCheck(int x, int y);
    void notifyPawnPromotion();
}
