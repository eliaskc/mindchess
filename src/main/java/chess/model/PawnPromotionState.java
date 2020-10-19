package chess.model;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PawnPromotionState implements GameState{

    private Map<Square, PieceType> promotionPieces = new HashMap<>();

    private Game context;
    private Square selectedSquare;


    PawnPromotionState(Square selectedSquare, Game context) {
        this.context = context;
        this.selectedSquare = selectedSquare;
        initPromotionPieces();
    }

    @Override
    public void handleInput(int x, int y) {
        Square selectedPromotion = new Square(x,y);
        if(promotionPieces.containsKey(selectedPromotion)){
            promote(selectedSquare,selectedPromotion);
            context.switchPlayer();
            context.notifyDrawPieces();
            context.setGameState(GameStateFactory.createNoPieceSelectedState(context));
        }
    }

    private void initPromotionPieces(){
        promotionPieces.put(new Square(20,0), PieceType.QUEEN);
        promotionPieces.put(new Square(21,0), PieceType.KNIGHT);
        promotionPieces.put(new Square(22,0), PieceType.ROOK);
        promotionPieces.put(new Square(23,0), PieceType.BISHOP);
    }

    private void promote(Square selectedSquare, Square selectedPromotion){
        Piece piece = new Piece(context.getBoard().getBoardMap().get(this.selectedSquare).getColor(), promotionPieces.get(selectedPromotion));
        context.getBoard().getBoardMap().put(selectedSquare, piece);
    }

    @Override
    public String getGameStatus() {
        return "Game ongoing";
    }

    @Override
    public boolean isGameOngoing() {
        return true;
    }
}
