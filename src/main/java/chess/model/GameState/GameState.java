package chess.model.GameState;

import chess.model.Piece;
import chess.model.Player;
import chess.model.Ply;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface GameState {
    void handleInput(int x, int y);
    boolean getIsGameOver();
    boolean getIsPlayerSwitch();
    String getGameStatus();
}
