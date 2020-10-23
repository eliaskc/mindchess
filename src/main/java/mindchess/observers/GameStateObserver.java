package mindchess.observers;

/**
 * observers used for notifying the game when changes in a state happens
 *
 * @author Erik Wessman, Elias Carlson, Elias Hallberg, Arvid Holmqvist
 */

public interface GameStateObserver {
    void notifyDrawLegalMoves();

    void notifySwitchPlayer();

    void notifyDrawPieces();

    void notifyDrawDeadPieces();

    void notifyKingInCheck(int x, int y);

    void notifyPawnPromotionSetup();

    void notifyPawnPromotionCleanUp();
}
