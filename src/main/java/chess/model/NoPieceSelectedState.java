package chess.model;

import chess.model.pieces.IPiece;
import chess.model.util.MovementLogicUtil;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class NoPieceSelectedState implements GameState {
    private Game context;

    NoPieceSelectedState(Game context) {
        this.context = context;
    }

    @Override
    public void handleInput(int x, int y) {
        Point selectedPoint = new Point(x, y);
        if (pointIsAPiece(selectedPoint) && isPieceMyColor(selectedPoint)) {
            fetchLegalMoves(selectedPoint);
            if (context.getLegalPoints().size() == 0) return;
            context.notifyDrawLegalMoves();
            context.setGameState(GameStateFactory.createPieceSelectedState(selectedPoint, context));
        }
    }

    /**
     * Adds all legal points the marked piece can move to to the legalPoints list
     */
    private void fetchLegalMoves(Point selectedPoint) {
        IPiece pieceToCheck = context.getBoard().fetchPieceOnPoint(selectedPoint);
        context.getLegalPoints().addAll(pieceToCheck.getMoveDelegate().fetchMoves(context.getBoard(), selectedPoint, pieceToCheck.getHasMoved()));
        //context.getLegalPoints().addAll(getEnPassantPoints(selectedPoint));
    }

    /*private List<Point> getEnPassantPoints(Point selectedPoint) {
        List<Point> enPassantPoints = new ArrayList<>();
        if (context.getPlies().size() == 0) return enPassantPoints;

        Ply lastPly = context.getPlies().get(context.getPlies().size() - 1);

        ChessColor pieceToMoveColor = context.getBoard().fetchPieceOnPointColor(selectedPoint);

        return MovementLogicUtil.getEnPassantPoints(selectedPoint, movedFrom, movedTo, pieceName, pieceToMoveColor);
    }*/


    private boolean pointIsAPiece(Point point) {
        if (context.getBoard().getBoardMap().containsKey(point)) return true;
        return false;
    }

    private boolean isPieceMyColor(Point point) {
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
