package chess.model.gameState;

import chess.model.IGameContext;

public class GameOverState implements GameState {
    private IGameContext context;
    private String resultStatus;

    GameOverState(String resultStatus,IGameContext context) {
        this.context = context;
        this.resultStatus = resultStatus;
        context.endGame();
    }

    @Override
    public void handleInput(int x, int y) {
        //Game over do nothing
    }

    @Override
    public String getGameStatus() {
        return resultStatus;
    }
}
