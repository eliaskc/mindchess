package chess.model.GameState;

import chess.model.IGameContext;
import chess.model.Piece;
import chess.model.PieceType;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PawnPromotionState implements GameState{

    private Map<Point, PieceType> promotionPieces = new HashMap<>();

    private IGameContext context;
    private Point markedPoint;
    private boolean isPlayerSwitch;

    public PawnPromotionState(Point markedPoint,boolean isPlayerSwitch,IGameContext context) {
        this.context = context;
        this.markedPoint = markedPoint;
        this.isPlayerSwitch = isPlayerSwitch;
        initPromotionPieces();
        System.out.println("Pawn promotion state");
    }

    @Override
    public void handleInput(int x, int y) {
        Point selectedPromotion = new Point(x,y);
        if(promotionPieces.containsKey(selectedPromotion)){
            promote(markedPoint,selectedPromotion);
            context.notifyPawnPromotion(context.getCurrentPlayer().getColor());
            context.notifyDrawPieces();
            context.setGameState(new NoPieceSelectedState(true,context));
        }

    }

    private void initPromotionPieces(){
        promotionPieces.put(new Point(0,1), PieceType.QUEEN);
        promotionPieces.put(new Point(1,1), PieceType.ROOK);
        promotionPieces.put(new Point(0,0), PieceType.BISHOP);
        promotionPieces.put(new Point(1,0), PieceType.KNIGHT);
    }

    private void promote(Point markedPoint, Point selectedPromotion){
        Piece piece = context.getBoardMap().get(markedPoint);
        piece.setPieceType(promotionPieces.get(selectedPromotion));
    }

    @Override
    public boolean getIsGameOver() {
        return false;
    }

    @Override
    public boolean getIsPlayerSwitch() {
        return isPlayerSwitch;
    }

    @Override
    public String getGameStatus() {
        return "Game ongoing";
    }
}
