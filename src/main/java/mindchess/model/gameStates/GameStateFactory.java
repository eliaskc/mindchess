package mindchess.model.gameStates;

import mindchess.model.*;
import mindchess.observers.GameStateObserver;

import java.util.List;

/**
 * A factory class for the different game states
 */
public class GameStateFactory {

    public static GameState createGameStatePieceSelected(Square selectedSquare, IBoard board, List<Ply> plies, List<Square> legalSquares, IGameContext context) {
        return new GameStatePieceSelected(selectedSquare, board, plies, legalSquares, context);
    }

    public static GameState createGameStateNoPieceSelected(IBoard board, List<Ply> plies, List<Square> legalSquares, IGameContext context) {
        return new GameStateNoPieceSelected(board, plies, legalSquares, context);
    }

    public static GameState createGameStateAIPlayerTurn(IBoard board, List<Ply> plies, List<Square> legalSquares, IGameContext context, GameStateObserver gameStateObserver, int difficulty) {
        GameStateAIPlayerTurn AIState = new GameStateAIPlayerTurn(board, legalSquares, plies, context, difficulty);
        AIState.addGameStateObserver(gameStateObserver);
        AIState.handleInput(0,0);
        return AIState;
    }
  
    public static GameState createGameStatePawnPromotion(Square selectedSquare, IBoard board, List<Ply> plies, List<Square> legalSquares, IGameContext context) {
        return new GameStatePawnPromotion(selectedSquare, board, plies, legalSquares, context);
    }

    public static GameState createGameStateGameOver(String resultStatus) {
        return new GameStateGameOver(resultStatus);
    }
}
