package mindchess.observers;

/**
 * Notifies when the game ends
 *
 * @author Erik Wessman, Elias Carlson, Elias Hallberg, Arvid Holmqvist
 */
public interface EndGameObserver {
    void showEndGameResult(String result);
}
