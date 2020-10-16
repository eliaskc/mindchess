package chess.model.gameState;

import chess.model.IGameContext;

import java.awt.*;

public class GameStateFactory {

    public static GameState createPieceSelectedState(Point selectedPoint, IGameContext context){
        return new PieceSelectedState(selectedPoint,context);
    }
    public static GameState createNoPieceSelectedState(IGameContext context){
        return new NoPieceSelectedState(context);
    }
    public static GameState createPawnPromotionState(Point selectedPoint, IGameContext context){
        return new PawnPromotionState(selectedPoint,context);
    }
    public static GameState createGameOverState(String resultStatus, IGameContext context){
        return new GameOverState(resultStatus,context);
    }
}
