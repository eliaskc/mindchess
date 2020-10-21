package chess.model;

import java.util.List;

public interface GameStateObserver {
    void notifyDrawLegalMoves();
    void notifySwitchPlayer();
    void notifyDrawPieces();
    void notifyDrawDeadPieces();
    void notifyKingInCheck(int x, int y);
    void notifyPawnPromotion();
}
