package chess.model;

import chess.model.pieces.IPiece;
import chess.model.pieces.PieceFactory;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class PawnPromotionState implements GameState {

    private Map<Point, String> promotionPieces = new HashMap<>();

    private Game context;
    private Point selectedPoint;


    PawnPromotionState(Point selectedPoint, Game context) {
        this.context = context;
        this.selectedPoint = selectedPoint;
        initPromotionPieces();
    }

    @Override
    public void handleInput(int x, int y) {
        Point selectedPromotion = new Point(x, y);
        if (promotionPieces.containsKey(selectedPromotion)) {
            promote(selectedPoint, selectedPromotion);
            context.switchPlayer();
            context.notifyDrawPieces();
            context.setGameState(GameStateFactory.createNoPieceSelectedState(context));
        }
    }

    private void initPromotionPieces() {
        promotionPieces.put(new Point(20, 0), "Queen");
        promotionPieces.put(new Point(21, 0), "Knight");
        promotionPieces.put(new Point(22, 0), "Rook");
        promotionPieces.put(new Point(23, 0), "Bishop");
    }

    private void promote(Point selectedPoint, Point selectedPromotion) {
        IPiece IPiece = null;
        try {
            IPiece = PieceFactory.createPiece(promotionPieces.get(selectedPromotion), context.getCurrentPlayerColor());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid Piece Name");
        }
        
        context.getBoard().getBoardMap().put(selectedPoint, IPiece);
    }

    @Override
    public java.lang.String getGameStatus() {
        return "Game ongoing";
    }

    @Override
    public boolean isGameOngoing() {
        return true;
    }
}
