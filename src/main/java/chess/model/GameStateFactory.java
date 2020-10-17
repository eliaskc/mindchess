package chess.model;

import java.awt.*;

public class GameStateFactory {

    public static GameState createPieceSelectedState(Point selectedPoint, Game context){
        return new PieceSelectedState(selectedPoint,context);
    }
    public static GameState createNoPieceSelectedState(Game context){
        return new NoPieceSelectedState(context);
    }
    public static GameState createPawnPromotionState(Point selectedPoint, Game context){
        return new PawnPromotionState(selectedPoint,context);
    }
    public static GameState createGameOverState(String resultStatus, Game context){
        return new GameOverState(resultStatus,context);
    }
}
