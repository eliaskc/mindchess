package mindchess.model;

import mindchess.model.enums.ChessColor;
import mindchess.model.gameStates.GameState;
import mindchess.observers.GameStateObserver;

/**
 * represents the GameStates' context
 *
 * @author Erik Wessman, Elias Carlson, Elias Hallberg, Arvid Holmqvist
 */
public interface IGameContext {
    /**
     * used in special cases when a state can call an other states delegated method
     *
     * @param x the horizontal chess coordinate of the input
     * @param y the vertical chess coordinate of the input
     */
    void handleBoardInput(int x, int y);

    /**
     * for the states to be able to change state
     *
     * @param gameState the state to switch to
     */
    void setGameState(GameState gameState);

    void addGameStateObserver(GameStateObserver gameStateObserver);

    String getCurrentPlayerName();

    ChessColor getCurrentPlayerColor();
}
