package chess.model;

import chess.model.GameState;

public class GameStateGameOver implements GameState {
    private String resultStatus;

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
}
