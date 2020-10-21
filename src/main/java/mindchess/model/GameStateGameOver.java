package mindchess.model;

import java.util.ArrayList;
import java.util.List;

public class GameStateGameOver implements GameState {
    private String resultStatus;
    private List<GameStateObserver> gameStateObservers = new ArrayList<>();;

    GameStateGameOver(String resultStatus) {
        this.resultStatus = resultStatus;
    }

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
