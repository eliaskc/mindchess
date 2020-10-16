package chess.model;

import chess.model.gameState.GameState;

import java.awt.Point;
import java.util.List;

public interface IGameContext {
    void setGameState(GameState gameState);
    GameState getGameState();
    Player getCurrentPlayer();
    List<Point> getLegalPoints();
    List<Ply> getPlies();
    Board getBoard();
    void notifyDrawPieces();
    void notifyDrawDeadPieces();
    void notifyDrawLegalMoves();
    void notifyPawnPromotion();
    void notifyEndGame();
}
