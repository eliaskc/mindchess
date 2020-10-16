package chess.model.gameState;

import chess.model.*;

import java.awt.*;

public class NoPieceSelectedState implements GameState {
    private IGameContext context;
    private Movement movement;

    NoPieceSelectedState(IGameContext context) {
        this.context = context;
        this.movement = new Movement(context.getBoard().getBoardMap(),context.getPlies());
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
    private void fetchLegalMoves(Point pointSelected) {
        context.getLegalPoints().addAll(movement.fetchLegalMoves(context.getBoard().getBoardMap().get(pointSelected), pointSelected));
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
}
