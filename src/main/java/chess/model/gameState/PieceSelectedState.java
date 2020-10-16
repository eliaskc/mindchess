package chess.model.gameState;

import chess.model.*;

import java.awt.*;

import static chess.model.ChessColor.BLACK;
import static chess.model.ChessColor.WHITE;
import static chess.model.PieceType.PAWN;

public class PieceSelectedState implements GameState {

    private Point selectedPoint;
    private IGameContext context;
    private Movement movement;
    private Piece takenPiece = null;

    PieceSelectedState(Point selectedPoint, IGameContext context) {
        this.selectedPoint = selectedPoint;
        this.context = context;
        this.movement = new Movement(context.getBoard().getBoardMap(), context.getPlies());
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
        if (context.getBoard().getBoardMap().containsKey(targetPoint) && !targetPoint.equals(selectedPoint)) {
            if (context.getBoard().getBoardMap().get(targetPoint).getColor()== context.getCurrentPlayer().getColor()) {
                clearAndDrawLegalMoves();
                context.setGameState(GameStateFactory.createNoPieceSelectedState(context));
                context.getGameState().handleInput(targetPoint.x, targetPoint.y);
                return;
            }
        }

        if (context.getLegalPoints().contains(targetPoint)) {
            move(selectedPoint,targetPoint);
            addMoveToPlies(selectedPoint, targetPoint);

            if (checkKingTaken()) {
                context.setGameState(GameStateFactory.createGameOverState(context.getCurrentPlayer().getName() + " has won the game",context));
                return;
            }

            context.switchPlayer();
            checkKingInCheck();

            if (checkPawnPromotion(targetPoint)) {
                context.switchPlayer();
                context.setGameState(GameStateFactory.createPawnPromotionState(targetPoint,context));
                clearAndDrawLegalMoves();
                return;
            }
        }
        context.setGameState(GameStateFactory.createNoPieceSelectedState(context));
        clearAndDrawLegalMoves();
    }

    private void move(Point selectedPoint, Point targetPoint) {
        makeSpecialMoves(selectedPoint, targetPoint);
        makeMoves(selectedPoint, targetPoint);

        context.notifyDrawPieces();
    }

    /**
     * Checks if any special moves are attempted and if so, performs the necessary actions
     *
     * @param selectedPoint
     * @param clickedPoint
     */
    private void makeSpecialMoves(Point selectedPoint, Point clickedPoint) {
        if (!context.getBoard().getBoardMap().containsKey(selectedPoint)) return;

        //castling
        if (movement.getCastlingPoints(context.getBoard().getBoardMap().get(selectedPoint), selectedPoint).size() != 0 && movement.getCastlingPoints(context.getBoard().getBoardMap().get(selectedPoint), selectedPoint).contains(clickedPoint)) {
            if (clickedPoint.x > selectedPoint.x) {
                makeMoves(new Point(clickedPoint.x + 1, clickedPoint.y), new Point(clickedPoint.x - 1, clickedPoint.y));
            } else if (clickedPoint.x < selectedPoint.x) {
                makeMoves(new Point(clickedPoint.x - 2, clickedPoint.y), new Point(clickedPoint.x + 1, clickedPoint.y));
            }
        }
        if (movement.getEnPassantPoints(context.getBoard().getBoardMap().get(selectedPoint), selectedPoint).size() != 0 && movement.getEnPassantPoints(context.getBoard().getBoardMap().get(selectedPoint), selectedPoint).contains(clickedPoint)) {
            if (context.getBoard().getBoardMap().get(selectedPoint).getColor() == WHITE) {
                takePiece(new Point(clickedPoint.x, clickedPoint.y + 1));
            } else if (context.getBoard().getBoardMap().get(selectedPoint).getColor() == BLACK) {
                takePiece(new Point(clickedPoint.x, clickedPoint.y - 1));
            }
        }
    }

    /**
     * Moves the marked piece to the clicked point
     * <p>
     */
    private void makeMoves(Point moveFrom, Point moveTo) {
        if (context.getBoard().getBoardMap().containsKey(moveTo)) {
            takePiece(moveTo);
        }

        context.getBoard().getBoardMap().put(moveTo, context.getBoard().getBoardMap().get(moveFrom));
        context.getBoard().getBoardMap().remove(moveFrom);
    }

    private void clearAndDrawLegalMoves(){
        context.getLegalPoints().clear();
        context.notifyDrawLegalMoves();
    }

    private void takePiece(Point pointToTake) {
        takenPiece = context.getBoard().getBoardMap().remove(pointToTake);
        context.getBoard().getDeadPieces().add(takenPiece);
        context.notifyDrawDeadPieces();
    }

    private void checkKingInCheck() {
        Point kingPoint = movement.fetchKingPoint(context.getCurrentPlayer().getColor());
        if (movement.isKingInCheck(kingPoint)) {
            context.notifyKingInCheck(kingPoint.x, kingPoint.y);
        }
    }

    private boolean checkKingTaken() {
        for (Piece p : context.getBoard().getDeadPieces()) {
            if (p.getPieceType() == PieceType.KING) return true;
        }
        return false;
    }

    /**
     * Checks if pawn a pawn is in a position to be promoted and initiates the promotion if so
     *
     * @param clickedPoint
     */
    private boolean checkPawnPromotion(Point clickedPoint) {
        if (context.getBoard().getBoardMap().get(clickedPoint).getPieceType() == PAWN) {
            if ((clickedPoint.y == 0 && context.getBoard().getBoardMap().get(clickedPoint).getColor() == WHITE) || (clickedPoint.y == 7 && context.getBoard().getBoardMap().get(clickedPoint).getColor() == BLACK)) {
                context.notifyPawnPromotion();
                return true;
            }
        }
        return false;
    }

    private void addMoveToPlies(Point selectedPoint, Point targetPoint) {
        Ply ply = new Ply(context.getCurrentPlayer().getName(), selectedPoint, targetPoint, context.getBoard().getBoardMap().get(targetPoint), takenPiece, context.getBoard().getBoardMap());
        context.getPlies().add(ply);
    }

    @Override
    public String getGameStatus() {
        return "Game ongoing";
    }
}
