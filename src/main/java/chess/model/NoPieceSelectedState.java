package chess.model;

import chess.model.pieces.PieceMovementLogic;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static chess.model.ChessColor.BLACK;
import static chess.model.ChessColor.WHITE;

public class NoPieceSelectedState implements GameState {
    private Game context;
    private PieceMovementLogic pieceMovementLogic = PieceMovementLogic.getInstance();

    NoPieceSelectedState(Game context) {
        this.context = context;
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
    private void fetchLegalMoves(Point selectedPoint) {
        context.getLegalPoints().addAll(context.getBoard().fetchPieceOnPoint(selectedPoint).fetchLegalMoves(selectedPoint));
        context.getLegalPoints().addAll(getEnPassantPoints(selectedPoint));
    }

    private List<Point> getEnPassantPoints(Point selectedPoint) {
        List<Point> enPassantPoints = new ArrayList<>();
        if (context.getPlies().size() == 0) return enPassantPoints;

        Ply lastPly = context.getPlies().get(context.getPlies().size() - 1);
        Point movedFrom = lastPly.getMovedFrom();
        Point movedTo = lastPly.getMovedTo();
        String pieceName = lastPly.getMovedPiece().getPieceName();

        ChessColor pieceToMoveColor = context.getBoard().fetchPieceOnPointColor(selectedPoint);

        if (pieceName.equals("Pawn") && !context.getBoard().pieceOnPointColorEquals(movedTo, pieceToMoveColor) && Math.abs(movedFrom.y - movedTo.y) == 2) {
            if ((movedTo.x == selectedPoint.x + 1 || movedFrom.x == selectedPoint.x - 1) && movedTo.y == selectedPoint.y) {
                if (context.getBoard().pieceOnPointColorEquals(movedTo, BLACK)) {
                    enPassantPoints.add(new Point(movedTo.x, movedTo.y - 1));
                } else if (context.getBoard().pieceOnPointColorEquals(movedTo, WHITE)) {
                    enPassantPoints.add(new Point(movedTo.x, movedTo.y + 1));
                }
                pieceMovementLogic.setEnPassantPossible(true);
            } else {
                pieceMovementLogic.setEnPassantPossible(false);
            }
        }
        return enPassantPoints;
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
