package chess.model;

import chess.model.GameState.GameState;

public interface IGameStateChanger {
    void setGameState(GameState gameState);
    void notifyDrawPieces();
    void notifyDrawDeadPieces();
    void notifyDrawLegalMoves();
}
