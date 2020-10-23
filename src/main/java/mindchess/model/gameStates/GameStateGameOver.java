package mindchess.model.gameStates;

import mindchess.observers.GameStateObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * The state which represent when the is over
 */
public class GameStateGameOver implements GameState {
    private final String resultStatus;
    private final List<GameStateObserver> gameStateObservers = new ArrayList<>();

    GameStateGameOver(String resultStatus) {
        this.resultStatus = resultStatus;
    }

    /**
     * Any input in this state will do nothing as the game has ended
     *
     * @param x
     * @param y
     */
    @Override
    public void handleInput(int x, int y) {
        //Game over do nothing
    }

    @Override
    public String getGameStatus() {
        return resultStatus;
    }

    @Override
    public boolean isGameOngoing() {
        return false;
    }

    @Override
    public void addGameStateObserver(GameStateObserver gameStateObserver) {
        this.gameStateObservers.add(gameStateObserver);
    }
}
