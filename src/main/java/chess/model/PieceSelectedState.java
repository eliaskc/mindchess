package chess.model;

import java.awt.*;

import static chess.model.ChessColor.BLACK;
import static chess.model.ChessColor.WHITE;
import static chess.model.PieceType.PAWN;

public class PieceSelectedState implements GameState {

    private Square selectedSquare;
    private Game context;
    private Movement movement;
    private Piece takenPiece = null;

    PieceSelectedState(Square selectedSquare, Game context) {
        this.selectedSquare = selectedSquare;
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
        Square targetSquare = new Square(x, y);
        if (context.getBoard().getBoardMap().containsKey(targetSquare) && !targetSquare.equals(selectedSquare) && context.getBoard().getBoardMap().get(targetSquare).getColor() == context.getCurrentPlayer().getColor()) {
            clearAndDrawLegalMoves();
            context.setGameState(GameStateFactory.createNoPieceSelectedState(context));
            context.handleBoardInput(targetSquare.getX(), targetSquare.getY());
            return;
        }

        if (context.getLegalSquares().contains(targetSquare)) {
            move(selectedSquare,targetSquare);
            addMoveToPlies(selectedSquare, targetSquare);
            context.notifyDrawPieces();

            if (checkKingTaken()) {
                context.setGameState(GameStateFactory.createGameOverState(context.getCurrentPlayer().getName() + " has won the game"));
                return;
            }

            if (checkPawnPromotion(targetSquare)) {
                context.setGameState(GameStateFactory.createPawnPromotionState(targetSquare,context));
                clearAndDrawLegalMoves();
                return;
            }

            context.switchPlayer();
            checkKingInCheck();
        }
        context.setGameState(GameStateFactory.createNoPieceSelectedState(context));
        clearAndDrawLegalMoves();
    }

    private void move(Square selectedSquare, Square targetSquare) {
        makeSpecialMoves(selectedSquare, targetSquare);
        makeMoves(selectedSquare, targetSquare);
    }

    /**
     * Checks if any special moves are attempted and if so, performs the necessary actions
     *
     * @param selectedSquare
     * @param targetSquare
     */
    private void makeSpecialMoves(Square selectedSquare, Square targetSquare) {
        if (!context.getBoard().getBoardMap().containsKey(selectedSquare)) return;

        //castling
        if (movement.getCastlingSquare(context.getBoard().getBoardMap().get(selectedSquare), selectedSquare).size() != 0 && movement.getCastlingSquare(context.getBoard().getBoardMap().get(selectedSquare), selectedSquare).contains(targetSquare)) {
            if (targetSquare.getX() > selectedSquare.getX()) {
                makeMoves(new Square(targetSquare.getX() + 1, targetSquare.getY()), new Square(targetSquare.getX() - 1, targetSquare.getY()));
            } else if (targetSquare.getX() < selectedSquare.getX()) {
                makeMoves(new Square(targetSquare.getX() - 2, targetSquare.getY()), new Square(targetSquare.getX() + 1, targetSquare.getY()));
            }
        }
        if (movement.getEnPassantSquares(context.getBoard().getBoardMap().get(selectedSquare), selectedSquare).size() != 0 && movement.getEnPassantSquares(context.getBoard().getBoardMap().get(selectedSquare), selectedSquare).contains(targetSquare)) {
            if (context.getBoard().getBoardMap().get(selectedSquare).getColor() == WHITE) {
                takePiece(new Square(targetSquare.getX(), targetSquare.getY() + 1));
            } else if (context.getBoard().getBoardMap().get(selectedSquare).getColor() == BLACK) {
                takePiece(new Square(targetSquare.getX(), targetSquare.getY() - 1));
            }
        }
    }

    /**
     * Moves the marked piece to the clicked point
     * <p>
     */
    private void makeMoves(Square moveFrom, Square moveTo) {
        if (context.getBoard().getBoardMap().containsKey(moveTo)) {
            takePiece(moveTo);
        }

        context.getBoard().getBoardMap().put(moveTo, context.getBoard().getBoardMap().get(moveFrom));
        context.getBoard().getBoardMap().remove(moveFrom);
    }

    private void clearAndDrawLegalMoves(){
        context.getLegalSquares().clear();
        context.notifyDrawLegalMoves();
    }

    private void takePiece(Square pieceOnSquareToTake) {
        takenPiece = context.getBoard().getBoardMap().remove(pieceOnSquareToTake);
        context.getBoard().getDeadPieces().add(takenPiece);
        context.notifyDrawDeadPieces();
    }

    private void checkKingInCheck() {
        Square kingSquare = movement.fetchKingSquare(context.getCurrentPlayer().getColor());
        if (movement.isKingInCheck(kingSquare)) {
            context.notifyKingInCheck(kingSquare.getX(), kingSquare.getY());
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
     * @param targetSquare
     */
    private boolean checkPawnPromotion(Square targetSquare) {
        if (context.getBoard().getBoardMap().get(targetSquare).getPieceType() == PAWN && ((targetSquare.getY() == 0 && context.getBoard().getBoardMap().get(targetSquare).getColor() == WHITE) || (targetSquare.getY() == 7 && context.getBoard().getBoardMap().get(targetSquare).getColor() == BLACK))) {
            context.notifyPawnPromotion();
            return true;
        }
        return false;
    }

    private void addMoveToPlies(Square selectedSquare, Square targetSquare) {
        Ply ply = new Ply(context.getCurrentPlayer().getName(), selectedSquare, targetSquare, context.getBoard().getBoardMap().get(targetSquare), takenPiece, context.getBoard().getBoardMap());
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
