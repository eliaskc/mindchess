package chess.model;

public interface GameState {
    void handleInput(int x, int y);
    String getGameStatus();
    boolean isGameOngoing();
}
