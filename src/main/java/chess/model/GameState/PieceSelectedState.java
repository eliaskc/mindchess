package chess.model.GameState;

import chess.model.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

import static chess.model.ChessColor.BLACK;
import static chess.model.ChessColor.WHITE;
import static chess.model.PieceType.PAWN;

public class PieceSelectedState implements GameState {

    private Point markedPoint;
    private IGameContext context;


    public PieceSelectedState(Point markedPoint, IGameContext context) {
        this.markedPoint = markedPoint;
        this.context = context;
        System.out.println("Piece selected state");
    }

    @Override
    public void handleInput(int x, int y) {
        checkMove(x,y);
    }

    /**
     * Checks if the latest click was on a point that is legal to move to
     * If it is, the move is made
     *
     * @param x
     * @param y
     */
    private void checkMove(int x, int y) {
        Point selectedPoint = new Point(x,y);
        if (context.getLegalPoints().contains(selectedPoint)) {
            context.getPlies().add(new Ply(markedPoint, selectedPoint, context.getBoardMap().get(markedPoint), context.getCurrentPlayer()));
            makeSpecialMoves(markedPoint, selectedPoint);
            move(markedPoint, selectedPoint);
            context.notifyDrawPieces();
            if(checkPawnPromotion(selectedPoint)){
                context.setGameState(new PawnPromotionState(selectedPoint,context));
                return;
            }
            context.switchPlayer();

        }
        context.setGameState(new NoPieceSelectedState(context));
        context.getLegalPoints().clear();
        context.notifyDrawLegalMoves();
    }

    /**
     * Checks if any special moves are attempted and if so, performs the necessary actions
     *
     * @param markedPoint
     * @param clickedPoint
     */
    private void makeSpecialMoves(Point markedPoint, Point clickedPoint) {
        //castling
        if (context.getMovement().getCastlingPoints().size() != 0 && context.getMovement().getCastlingPoints().contains(clickedPoint)) {
            if (clickedPoint.x > markedPoint.x) {
                move(new Point(clickedPoint.x + 1, clickedPoint.y), new Point(clickedPoint.x - 1, clickedPoint.y));
            } else if (clickedPoint.x < markedPoint.x) {
                move(new Point(clickedPoint.x - 2, clickedPoint.y), new Point(clickedPoint.x + 1, clickedPoint.y));
            }
        }

        //en passant
        if (context.getMovement().getEnPassantPoints().size() != 0 && context.getMovement().getEnPassantPoints().contains(clickedPoint)) {
            if (context.getBoardMap().get(markedPoint).getColor() == WHITE) {
                takePiece(new Point(clickedPoint.x, clickedPoint.y + 1));
            } else if (context.getBoardMap().get(markedPoint).getColor() == BLACK) {
                takePiece(new Point(clickedPoint.x, clickedPoint.y - 1));
            }
        }
    }
    /**
     * Moves the marked piece to the clicked point
     * <p>
     */
    private void move(Point moveFrom, Point moveTo) {
        if (context.getBoardMap().containsKey(moveTo)) {
            takePiece(moveTo);
        }

        context.getBoardMap().put(moveTo, context.getBoardMap().get(moveFrom));
        context.getBoardMap().remove(moveFrom);
    }

    private void takePiece(Point pointToTake) {
        context.getDeadPieces().add(context.getBoardMap().remove(pointToTake));
        context.notifyDrawDeadPieces();
    }

    /**
     * Checks if pawn a pawn is in a position to be promoted and initiates the promotion if so
     *
     * @param clickedPoint
     */
    private boolean checkPawnPromotion(Point clickedPoint) {
        if (context.getBoardMap().get(clickedPoint).getPieceType() == PAWN) {
            if ((clickedPoint.y == 0 && context.getBoardMap().get(clickedPoint).getColor() == WHITE) || (clickedPoint.y == 7 && context.getBoardMap().get(clickedPoint).getColor() == BLACK)) {
                context.notifyPawnPromotion(context.getBoardMap().get(clickedPoint).getColor());
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isGameOver() {
        return false;
    }

    @Override
    public String getWinnerName() {
        return "null";
    }
}
