package chess.model;

import chess.model.GameState.GameState;

import java.awt.Point;
import java.util.List;
import java.util.Map;

public interface IGameContext {
    void setGameState(GameState gameState);
    Player getCurrentPlayer();
    List<Point> getLegalPoints();
    List<Ply> getPlies();
    Board getBoard();
    void notifyDrawPieces();
    void notifyDrawDeadPieces();
    void notifyDrawLegalMoves();
    void notifyPawnPromotion();
}
