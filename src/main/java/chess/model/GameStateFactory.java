package chess.model;

import java.util.List;

public class GameStateFactory {

    public static GameState createPieceSelectedState(Square selectedSquare, Board board, List<Ply> plies, List<Square> legalSquares, GameStateObserver gameStateObserver, IGameContext context){
        return new GameStatePieceSelected(selectedSquare,board,plies,legalSquares, gameStateObserver,context);
    }

    public static GameState createNoPieceSelectedState(Board board, List<Ply> plies, List<Square> legalSquares, GameStateObserver gameStateObserver, IGameContext context) {
        return new GameStateNoPieceSelected(board,plies,legalSquares, gameStateObserver,context);
    }

    public static GameState createPawnPromotionState(Square selectedSquare, Board board, List<Ply> plies, List<Square> legalSquares, GameStateObserver gameStateObserver, IGameContext context){
        return new GameStatePawnPromotion(selectedSquare,board,plies,legalSquares, gameStateObserver,context);
    }

    public static GameState createGameOverState(String resultStatus) {
        return new GameStateGameOver(resultStatus);
    }
}
