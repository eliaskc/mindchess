package chess.model.GameState;

import chess.model.GameState.GameState;
import chess.model.IGameContext;
import chess.model.Piece;
import chess.model.Ply;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameWonState implements GameState {
    private IGameContext context;

    public GameWonState(IGameContext context) {
        this.context = context;
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
        return context.getCurrentPlayer().getName() + " has won the game";
    }
}
