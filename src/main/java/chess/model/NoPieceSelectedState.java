package chess.model;

import java.awt.*;

public class NoPieceSelectedState implements GameState {
    private Game context;
    private Movement movement;

    NoPieceSelectedState(Game context) {
        this.context = context;
        this.movement = new Movement(context.getBoard().getBoardMap(),context.getPlies());
    }

    @Override
    public void handleInput(int x, int y) {
        Square selectedSquare = new Square(x,y);
        if(SquareIsAPiece(selectedSquare) && isPieceMyColor(selectedSquare)) {
            fetchLegalMoves(selectedSquare);
            if(context.getLegalSquares().size() == 0) return;
            context.notifyDrawLegalMoves();
            context.setGameState(GameStateFactory.createPieceSelectedState(selectedSquare,context));
        }
    }

    /**
     * Adds all legal squares the marked piece can move to to the legalSquares list
     */
    private void fetchLegalMoves(Square squareSelected) {
        context.getLegalSquares().addAll(movement.fetchLegalMoves(context.getBoard().getBoardMap().get(squareSelected), squareSelected));
    }


    private boolean SquareIsAPiece(Square square){
        if(context.getBoard().getBoardMap().containsKey(square)) return true;
        return false;
    }

    private boolean isPieceMyColor(Square square){
        return context.getBoard().getBoardMap().get(square).getColor() == context.getCurrentPlayer().getColor();
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
