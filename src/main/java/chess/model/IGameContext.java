package chess.model;

public interface IGameContext {
    void setGameState(GameState gameState);
    void addGameStateObserver(GameStateObserver gameStateObserver);
    String getCurrentPlayerName();
    ChessColor getCurrentPlayerColor();

}
