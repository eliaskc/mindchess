package chess.model.GameState;

public class GameDrawState implements GameState {
    @Override
    public void handleInput(int x, int y) {
        System.out.println("Game ended in a draw");
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
        return "Game ended in a draw";
    }
}
