package chess.model;

import chess.model.pieces.IPiece;
import chess.model.pieces.PieceMovementLogic;

import java.awt.*;

import static chess.model.ChessColor.BLACK;
import static chess.model.ChessColor.WHITE;

public class PieceSelectedState implements GameState {

    private Point selectedPoint;
    private Game context;
    private IPiece takenIPiece = null;
    private PieceMovementLogic pieceMovementLogic = PieceMovementLogic.getInstance();

    PieceSelectedState(Point selectedPoint, Game context) {
        this.selectedPoint = selectedPoint;
        this.context = context;
    }

    /**
     * Checks if the latest click was on a point that is legal to move to
     * If it is, the move is made
     *
     * @param x
     * @param y
     */
    @Override
    public void handleInput(int x, int y) {
        Point targetPoint = new Point(x, y);
        if (context.getBoard().getBoardMap().containsKey(targetPoint) && !targetPoint.equals(selectedPoint) && context.getBoard().getBoardMap().get(targetPoint).getColor() == context.getCurrentPlayer().getColor()) {
            clearAndDrawLegalMoves();
            context.setGameState(GameStateFactory.createNoPieceSelectedState(context));
            context.handleBoardInput(targetPoint.x, targetPoint.y);
            return;
        }

        if (context.getLegalPoints().contains(targetPoint)) {
            move(selectedPoint,targetPoint);
            addMoveToPlies(selectedPoint, targetPoint);

            if (checkKingTaken()) {
                context.setGameState(GameStateFactory.createGameOverState(context.getCurrentPlayer().getName() + " has won the game"));
                return;
            }

            if (checkPawnPromotion(targetPoint)) {
                context.setGameState(GameStateFactory.createPawnPromotionState(targetPoint,context));
                clearAndDrawLegalMoves();
                return;
            }

            context.switchPlayer();
            //checkKingInCheck();
        }
        context.setGameState(GameStateFactory.createNoPieceSelectedState(context));
        clearAndDrawLegalMoves();
    }

    private void move(Point selectedPoint, Point targetPoint) {
        makeSpecialMoves(selectedPoint, targetPoint);
        makeMove(selectedPoint, targetPoint);

        context.notifyDrawPieces();
    }

    /**
     * Checks if any special moves are attempted and if so, performs the necessary actions
     *
     * @param selectedPoint
     * @param clickedPoint
     */
    //TODO
    private void makeSpecialMoves(Point selectedPoint, Point clickedPoint) {
        if (!context.getBoard().getBoardMap().containsKey(selectedPoint)) return;

        //castling
        if (pieceMovementLogic.isCastlingPossible()) {
            if (clickedPoint.x > selectedPoint.x) {
                makeMove(new Point(clickedPoint.x + 1, clickedPoint.y), new Point(clickedPoint.x - 1, clickedPoint.y));
            } else if (clickedPoint.x < selectedPoint.x) {
                makeMove(new Point(clickedPoint.x - 2, clickedPoint.y), new Point(clickedPoint.x + 1, clickedPoint.y));
            }
            pieceMovementLogic.setCastlingPossible(false);
        }


        if (pieceMovementLogic.isEnPassantPossible()) {
            if (context.getBoard().pieceOnPointColorEquals(selectedPoint, WHITE)) {
                takePiece(new Point(clickedPoint.x, clickedPoint.y + 1));
            } else if (context.getBoard().pieceOnPointColorEquals(selectedPoint, BLACK)) {
                takePiece(new Point(clickedPoint.x, clickedPoint.y - 1));
            }
            pieceMovementLogic.setEnPassantPossible(false);
        }
    }

    /**
     * Moves the marked piece to the clicked point
     * <p>
     */
    private void makeMove(Point moveFrom, Point moveTo) {
        if (context.getBoard().getBoardMap().containsKey(moveTo)) {
            takePiece(moveTo);
        }
        context.getBoard().markPieceOnPointHasMoved(moveFrom);
        context.getBoard().getBoardMap().put(moveTo, context.getBoard().getBoardMap().get(moveFrom));
        context.getBoard().getBoardMap().remove(moveFrom);
    }

    private void clearAndDrawLegalMoves(){
        context.getLegalPoints().clear();
        context.notifyDrawLegalMoves();
    }

    private void takePiece(Point pointToTake) {
        takenIPiece = context.getBoard().getBoardMap().remove(pointToTake);
        context.getBoard().getDeadPieces().add(takenIPiece);
        context.notifyDrawDeadPieces();
    }

    //TODO
    /*private void checkKingInCheck() {
        Point kingPoint = pieceMovementLogic.fetchKingPoint(context.getCurrentPlayer().getColor());
        if (pieceMovementLogic.isKingInCheck(kingPoint)) {
            context.notifyKingInCheck(kingPoint.x, kingPoint.y);
        }
    }*/

    private boolean checkKingTaken() {
        for (IPiece p : context.getBoard().getDeadPieces()) {
            if (p.getPieceName().equals("King")) return true;
        }
        return false;
    }

    /**
     * Checks if pawn a pawn is in a position to be promoted and initiates the promotion if so
     *
     * @param clickedPoint
     */
    private boolean checkPawnPromotion(Point clickedPoint) {
        if (context.getBoard().getBoardMap().get(clickedPoint).getPieceName().equals("Pawn") && ((clickedPoint.y == 0 && context.getBoard().getBoardMap().get(clickedPoint).getColor() == WHITE) || (clickedPoint.y == 7 && context.getBoard().getBoardMap().get(clickedPoint).getColor() == BLACK))) {
            context.notifyPawnPromotion();
            return true;
        }
        return false;
    }

    private void addMoveToPlies(Point selectedPoint, Point targetPoint) {
        Ply ply = new Ply(context.getCurrentPlayer().getName(), selectedPoint, targetPoint, context.getBoard().getBoardMap().get(targetPoint), takenIPiece, context.getBoard().getBoardMap());
        context.getPlies().add(ply);
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
