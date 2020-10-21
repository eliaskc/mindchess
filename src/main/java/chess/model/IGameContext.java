package chess.model;

public interface IGameContext {
    void setGameState(GameState gameState);
    GameState getGameState();
    String getCurrentPlayerName();
    ChessColor getCurrentPlayerColor();

}
