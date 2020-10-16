package chess.model.gameState;

public interface GameState {
    void handleInput(int x, int y);
    boolean getIsGameOver();
    boolean getIsPlayerSwitch();
    String getGameStatus();
}
