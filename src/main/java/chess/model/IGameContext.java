package chess.model;

import chess.model.GameState.GameState;

import java.awt.Point;
import java.util.List;
import java.util.Map;

public interface IGameContext {
    enum GameStates {
        NoPieceSelected,
        PieceSelected,
        GameOver,
        GameInactive
    }
    void setGameState(GameStates gameState);
    GameState getGameState();
    Player getCurrentPlayer();
    List<Point> getLegalPoints();
    List<Piece> getDeadPieces();
    List<Ply> getPlies();
    Map<Point,Piece> getBoardMap();
    Movement getMovement();
    void notifyDrawPieces();
    void notifyDrawDeadPieces();
    void notifyDrawLegalMoves();
    void notifySwitchedPlayer();
}
