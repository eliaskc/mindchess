package chess.model.GameState;

import chess.model.*;

import java.awt.*;

import static chess.model.ChessColor.BLACK;
import static chess.model.ChessColor.WHITE;
import static chess.model.PieceType.PAWN;

public class PieceSelectedState implements GameState {

    private Point markedPoint;
    private IGameContext context;
    private boolean isPlayerSwitch;


    public PieceSelectedState(Point markedPoint,boolean isPlayerSwitch, IGameContext context) {
        this.markedPoint = markedPoint;
        this.context = context;
        this.isPlayerSwitch = isPlayerSwitch;
        System.out.println("Piece selected state");
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
        Point selectedPoint = new Point(x,y);
        isPlayerSwitch = false;
        if (context.getLegalPoints().contains(selectedPoint)) {
            move(markedPoint,selectedPoint);
            if(checkPawnPromotion(selectedPoint)){
                context.setGameState(new PawnPromotionState(selectedPoint,false,context));
                clearDrawLegalMoves();
                return;
            }
        }
        context.setGameState(new NoPieceSelectedState(isPlayerSwitch,context));
        clearDrawLegalMoves();
    }

    private void move(Point markedPoint, Point selectedPoint){
        addMoveToPlies(markedPoint, selectedPoint);
        makeMoves(markedPoint, selectedPoint);
        makeSpecialMoves(markedPoint, selectedPoint);
        if(checkKingTaken()){
            context.setGameState(new GameWonState(context));
            return;
        }
        context.notifyDrawPieces();
        isPlayerSwitch = true;

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
                makeMoves(new Point(clickedPoint.x + 1, clickedPoint.y), new Point(clickedPoint.x - 1, clickedPoint.y));
            } else if (clickedPoint.x < markedPoint.x) {
                makeMoves(new Point(clickedPoint.x - 2, clickedPoint.y), new Point(clickedPoint.x + 1, clickedPoint.y));
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
    private void makeMoves(Point moveFrom, Point moveTo) {
        if (context.getBoardMap().containsKey(moveTo)) {
            takePiece(moveTo);
        }

        context.getBoardMap().put(moveTo, context.getBoardMap().get(moveFrom));
        context.getBoardMap().remove(moveFrom);
    }

    private void clearDrawLegalMoves(){
        context.getLegalPoints().clear();
        context.notifyDrawLegalMoves();
    }

    private void takePiece(Point pointToTake) {
        context.getDeadPieces().add(context.getBoardMap().remove(pointToTake));
        context.notifyDrawDeadPieces();
    }

    private boolean checkKingTaken(){
        for (Piece p: context.getDeadPieces()) {
            if(p.getPieceType() == PieceType.KING) return true;
        }
        return false;
    }

    /**
     * Checks if pawn a pawn is in a position to be promoted and initiates the promotion if so
     *
     * @param clickedPoint
     */
    private boolean checkPawnPromotion(Point clickedPoint) {
        if (context.getBoardMap().get(clickedPoint).getPieceType() == PAWN) {
            if ((clickedPoint.y == 0 && context.getBoardMap().get(clickedPoint).getColor() == WHITE) || (clickedPoint.y == 7 && context.getBoardMap().get(clickedPoint).getColor() == BLACK)) {
                context.notifyPawnPromotion();
                return true;
            }
        }
        return false;
    }

    private void addMoveToPlies(Point markedPoint,Point selectedPoint){
        context.getPlies().add(new Ply(markedPoint, selectedPoint, context.getBoardMap().get(markedPoint), context.getCurrentPlayer()));
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
