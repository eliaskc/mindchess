package chess.model;

public interface IGameContext {
    void setGameState(GameState gameState);
    String getCurrentPlayerName();
    ChessColor getCurrentPlayerColor();

}
