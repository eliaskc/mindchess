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
    }

    @Override
    public void handleInput(int x, int y) {
        Point selectedPromotion = new Point(x,y);
        if(promotionPieces.containsKey(selectedPromotion)){
            promote(markedPoint,selectedPromotion);
            context.notifyPawnPromotion();
            context.notifyDrawPieces();
            context.setGameState(new NoPieceSelectedState(false,context));
        }
    }

    private void initPromotionPieces(){
        promotionPieces.put(new Point(20,0), PieceType.QUEEN);
        promotionPieces.put(new Point(21,0), PieceType.KNIGHT);
        promotionPieces.put(new Point(22,0), PieceType.ROOK);
        promotionPieces.put(new Point(23,0), PieceType.BISHOP);
    }

    private void promote(Point markedPoint, Point selectedPromotion){
        Piece piece = context.getBoard().getBoardMap().get(markedPoint);
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
