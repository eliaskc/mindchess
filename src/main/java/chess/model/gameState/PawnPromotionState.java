package chess.model.gameState;

import chess.model.IGameContext;
import chess.model.Piece;
import chess.model.PieceType;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PawnPromotionState implements GameState{

    private Map<Point, PieceType> promotionPieces = new HashMap<>();

    private IGameContext context;
    private Point selectedPoint;


    PawnPromotionState(Point selectedPoint, IGameContext context) {
        this.context = context;
        this.selectedPoint = selectedPoint;
        initPromotionPieces();
    }

    @Override
    public void handleInput(int x, int y) {
        Point selectedPromotion = new Point(x,y);
        if(promotionPieces.containsKey(selectedPromotion)){
            promote(selectedPoint,selectedPromotion);
            context.switchPlayer();
            context.notifyPawnPromotion();
            context.notifyDrawPieces();
            context.setGameState(GameStateFactory.createNoPieceSelectedState(context));
        }
    }

    private void initPromotionPieces(){
        promotionPieces.put(new Point(20,0), PieceType.QUEEN);
        promotionPieces.put(new Point(21,0), PieceType.KNIGHT);
        promotionPieces.put(new Point(22,0), PieceType.ROOK);
        promotionPieces.put(new Point(23,0), PieceType.BISHOP);
    }

    private void promote(Point selectedPoint, Point selectedPromotion){
        Piece piece = new Piece(context.getBoard().getBoardMap().get(selectedPoint).getColor(), promotionPieces.get(selectedPromotion));
        context.getBoard().getBoardMap().put(selectedPoint, piece);
    }

    @Override
    public String getGameStatus() {
        return "Game ongoing";
    }
}
