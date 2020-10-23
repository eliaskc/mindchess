package mindchess.model;

import mindchess.model.enums.ChessColor;
import mindchess.model.enums.PlayerType;
import mindchess.model.gameStates.GameState;
import mindchess.observers.GameStateObserver;

/**
 * represents the GameStates' context
 */
public interface IGameContext {
    /**
     * used in special cases when a state can call an other states delegated method
     *
     * @param x
     * @param y
     */
    void handleBoardInput(int x, int y);

    /**
     * for the states to be able to change state
     *
     * @param gameState
     */
  
    void setGameState(GameState gameState);

    void addGameStateObserver(GameStateObserver gameStateObserver);

    String getCurrentPlayerName();

    ChessColor getCurrentPlayerColor();

    PlayerType getCurrentPlayerType();
}
