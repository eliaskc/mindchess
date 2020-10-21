package chess.model;

public interface IGameContext {
    void handleBoardInput(int x, int y);
    void setGameState(GameState gameState);
    void addGameStateObserver(GameStateObserver gameStateObserver);
    String getCurrentPlayerName();
    ChessColor getCurrentPlayerColor();

}
