package chess.model;

public class GameOverState implements GameState {
    private String resultStatus;

    GameOverState(String resultStatus) {
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
