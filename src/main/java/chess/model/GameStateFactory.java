package chess.model;

import java.awt.*;

public class GameStateFactory {

    public static GameState createPieceSelectedState(Square selectedSquare, Game context){
        return new PieceSelectedState(selectedSquare,context);
    }
    public static GameState createNoPieceSelectedState(Game context){
        return new NoPieceSelectedState(context);
    }
    public static GameState createPawnPromotionState(Square selectedSquare, Game context){
        return new PawnPromotionState(selectedSquare,context);
    }
    public static GameState createGameOverState(String resultStatus){
        return new GameOverState(resultStatus);
    }
}
