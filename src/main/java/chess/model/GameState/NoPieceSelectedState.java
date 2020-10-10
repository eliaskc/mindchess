package chess.model.GameState;

import chess.model.*;

import java.awt.*;

public class NoPieceSelectedState implements GameState {
    private IGameContext context;
    private Point markedPoint;

    public NoPieceSelectedState(IGameContext context) {
        this.context = context;
    }


    @Override
    public void handleInput(int x, int y) {
        markedPoint = new Point(x,y);
        if(pointIsAPiece(markedPoint) && isPieceMyColor(markedPoint)) {
            fetchLegalMoves(markedPoint);
            if(context.getLegalPoints().size() == 0) return;
            notifyDrawLegalMoves();
            context.setGameState(IGameContext.GameStates.PieceSelected);
            context.getGameState().setMarkedPoint(new Point(x,y));
        }
    }

    /**
     * Adds all legal points the marked piece can move to to the legalPoints list
     */
    private void fetchLegalMoves(Point pointSelected) {
        context.getLegalPoints().addAll(context.getMovement().pieceMoveDelegation(context.getBoardMap().get(pointSelected), pointSelected));
    }


    private boolean pointIsAPiece(Point point){
        if(context.getBoardMap().containsKey(point)) return true;
        return false;
    }

    private void notifyDrawLegalMoves() {
        context.notifyDrawLegalMoves();
    }

    private boolean isPieceMyColor(Point point){
        return context.getBoardMap().get(point).getColor() == context.getCurrentPlayer().getColor();
    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public String getWinnerName() {
        return "null";
    }

    @Override
    public void setMarkedPoint(Point markedPoint) {
        this.markedPoint = markedPoint;
    }
}
