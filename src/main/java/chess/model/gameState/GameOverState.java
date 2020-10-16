package chess.model.gameState;

import chess.model.IGameContext;

public class GameOverState implements GameState {
    private IGameContext context;
    private String resultStatus;

    public GameOverState(String resultStatus,IGameContext context) {
        this.context = context;
        this.resultStatus = resultStatus;
    }

    @Override
    public void handleInput(int x, int y) {
        //Game over do nothing
    }

    @Override
    public boolean getIsGameOver() {
        return true;
    }

    @Override
    public boolean getIsPlayerSwitch() {
        return false;
    }

    @Override
    public String getGameStatus() {
        return resultStatus;
    }
}
