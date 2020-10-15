package chess.model.GameState;

import chess.model.*;

import java.awt.*;

public class NoPieceSelectedState implements GameState {
    private IGameContext context;
    private boolean isPlayerSwitch;
    private Movement movement;

    public NoPieceSelectedState(boolean isPlayerSwitch, IGameContext context) {
        this.context = context;
        this.isPlayerSwitch = isPlayerSwitch;
        this.movement = new Movement(context.getBoard().getBoardMap(),context.getPlies());
    }




    @Override
    public void handleInput(int x, int y) {
        Point markedPoint = new Point(x,y);
        isPlayerSwitch = false;
        if(pointIsAPiece(markedPoint) && isPieceMyColor(markedPoint)) {
            fetchLegalMoves(markedPoint);
            if(context.getLegalPoints().size() == 0) return;
            context.notifyDrawLegalMoves();
            context.setGameState(new PieceSelectedState(markedPoint,false,context));
        }
    }

    /**
     * Adds all legal points the marked piece can move to to the legalPoints list
     */
    private void fetchLegalMoves(Point pointSelected) {
        context.getLegalPoints().addAll(movement.pieceMoveDelegation(context.getBoard().getBoardMap().get(pointSelected), pointSelected));
    }


    private boolean pointIsAPiece(Point point){
        if(context.getBoard().getBoardMap().containsKey(point)) return true;
        return false;
    }

    private boolean isPieceMyColor(Point point){
        return context.getBoard().getBoardMap().get(point).getColor() == context.getCurrentPlayer().getColor();
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
