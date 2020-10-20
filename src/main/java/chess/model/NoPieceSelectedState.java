package chess.model;

import chess.model.pieces.IPiece;

import java.util.ArrayList;
import java.util.List;

public class NoPieceSelectedState implements GameState {
    private Game context;

    NoPieceSelectedState(Game context) {
        this.context = context;
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
    private void fetchLegalMoves(Square selectedSquare) {
        IPiece pieceToCheck = context.getBoard().fetchPieceOnSquare(selectedSquare);
        context.getLegalSquares().addAll(pieceToCheck.getMoveDelegate().fetchMoves(context.getBoard(), selectedSquare, pieceToCheck.getHasMoved(), true));
        context.getLegalSquares().addAll(getEnPassantSquares(selectedSquare));
    }

    private List<Square> getEnPassantSquares(Square selectedSquare) {
        List<Square> enPassantPoints = new ArrayList<>();
        if (context.getPlies().size() == 0) return enPassantPoints;

        Ply lastPly = context.getPlies().get(context.getPlies().size() - 1);

        return MovementLogicUtil.getEnPassantSquares(lastPly, selectedSquare, context.getBoard());
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
