package mindchess.model;

import java.util.List;

public class GameStateFactory {

    public static GameState createGameStatePieceSelected(Square selectedSquare, Board board, List<Ply> plies, List<Square> legalSquares, IGameContext context){
        return new GameStatePieceSelected(selectedSquare,board,plies,legalSquares,context);
    }

    public static GameState createGameStateNoPieceSelected(Board board, List<Ply> plies, List<Square> legalSquares, IGameContext context) {
        return new GameStateNoPieceSelected(board,plies,legalSquares,context);
    }

    public static GameState createGameStatePawnPromotion(Square selectedSquare, Board board, List<Ply> plies, List<Square> legalSquares, IGameContext context){
        return new GameStatePawnPromotion(selectedSquare,board,plies,legalSquares,context);
    }

    public static GameState createGameStateGameOver(String resultStatus) {
        return new GameStateGameOver(resultStatus);
    }
}
