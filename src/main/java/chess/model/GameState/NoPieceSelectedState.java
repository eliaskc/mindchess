package chess.model.GameState;

import chess.model.*;

import java.awt.*;

public class NoPieceSelectedState implements GameState {
    private IGameContext context;
    private boolean isPlayerSwitch;

    public NoPieceSelectedState(boolean isPlayerSwitch, IGameContext context) {
        this.context = context;
        this.isPlayerSwitch = isPlayerSwitch;
        System.out.println("No piece selected state");
    }


    @Override
    public void handleInput(int x, int y) {
        Point markedPoint = new Point(x,y);
        if(pointIsAPiece(markedPoint) && isPieceMyColor(markedPoint)) {
            fetchLegalMoves(markedPoint);
            if(context.getLegalPoints().size() == 0) return;
            context.setGameState(new PieceSelectedState(markedPoint,false,context));
        }
        isPlayerSwitch = false;
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

    private boolean isPieceMyColor(Point point){
        return context.getBoardMap().get(point).getColor() == context.getCurrentPlayer().getColor();
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
    public boolean getIsGameDraw() {
        return false;
    }

    @Override
    public boolean getIsPawnPromotion() {
        return false;
    }
}
