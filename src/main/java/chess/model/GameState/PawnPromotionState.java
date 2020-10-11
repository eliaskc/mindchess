package chess.model.GameState;

import chess.model.IGameContext;
import chess.model.Piece;
import chess.model.PieceType;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PawnPromotionState implements GameState{

    private Map<Point, PieceType> promotionPieces = new HashMap<>();

    IGameContext context;
    Point markedPoint;

    public PawnPromotionState(Point markedPoint,IGameContext context) {
        this.context = context;
        this.markedPoint = markedPoint;
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
            context.switchPlayer();
            context.setGameState(new NoPieceSelectedState(context));
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
        System.out.println(piece.getPieceType());
        piece.setPieceType(promotionPieces.get(selectedPromotion));
        System.out.println(piece.getPieceType());
    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public String getWinnerName() {
        return "null";
    }
}
