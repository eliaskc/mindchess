package chess.model;

import chess.model.GameState.GameState;

public interface IGameStateChanger {
    enum GameStates {
        NoPieceSelected,
        PieceSelected,
        GameOver,
        GameInactive
    }
    void setGameState(GameStates gameState);
    GameState getGameState();
    void notifyDrawPieces();
    void notifyDrawDeadPieces();
    void notifyDrawLegalMoves();
    void notifySwitchedPlayer();
}
