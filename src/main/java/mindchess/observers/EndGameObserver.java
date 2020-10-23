package mindchess.observers;

/**
 * Notifies when the game ends
 */
public interface EndGameObserver {
    void showEndGameResult(String result);
}
