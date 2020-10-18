package chess.model;

import chess.model.pieces.PieceMovementLogic;

import java.awt.*;

public class NoPieceSelectedState implements GameState {
    private Game context;
    private PieceMovementLogic pieceMovementLogic;

    NoPieceSelectedState(Game context) {
        this.context = context;
        this.pieceMovementLogic = new PieceMovementLogic(context.getBoard());
    }

    @Override
    public void handleInput(int x, int y) {
        Point selectedPoint = new Point(x,y);
        if(pointIsAPiece(selectedPoint) && isPieceMyColor(selectedPoint)) {
            fetchLegalMoves(selectedPoint);
            if(context.getLegalPoints().size() == 0) return;
            context.notifyDrawLegalMoves();
            context.setGameState(GameStateFactory.createPieceSelectedState(selectedPoint,context));
        }
    }

    /**
     * Adds all legal points the marked piece can move to to the legalPoints list
     */
    //TODO
    private void fetchLegalMoves(Point pointSelected) {
        context.getLegalPoints().addAll(context.getBoard().fetchPieceOnPoint(pointSelected).fetchLegalMoves(pointSelected));
    }


    private boolean pointIsAPiece(Point point){
        if(context.getBoard().getBoardMap().containsKey(point)) return true;
        return false;
    }

    private boolean isPieceMyColor(Point point){
        return context.getBoard().getBoardMap().get(point).getColor() == context.getCurrentPlayer().getColor();
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
