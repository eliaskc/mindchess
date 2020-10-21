package mindchess.model;

public interface GameState {
    void handleInput(int x, int y);
    String getGameStatus();
    boolean isGameOngoing();
    void addGameStateObserver(GameStateObserver gameStateObserver);
}
