package chess.model;

import chess.model.pieces.IPiece;

import static chess.model.ChessColor.*;
import static chess.model.PieceType.*;
import static chess.model.SquareType.*;

public class PieceSelectedState implements GameState {

    private Square selectedSquare;
    private Game context;
    private IPiece takenPiece = null;

    PieceSelectedState(Square selectedSquare, Game context) {
        this.selectedSquare = selectedSquare;
        this.context = context;
    }

    /**
     * Checks if the latest click was on a square that is legal to move to
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
            targetSquare = context.getLegalSquareByCoordinates(x, y);
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
            checkKingInCheck(context.getCurrentPlayerColor());
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
        if (targetSquare.getSquareType() == CASTLING) {
            if (targetSquare.getX() > selectedSquare.getX()) {
                makeMoves(new Square(targetSquare.getX() + 1, targetSquare.getY()), new Square(targetSquare.getX() - 1, targetSquare.getY()));
            } else if (targetSquare.getX() < selectedSquare.getX()) {
                makeMoves(new Square(targetSquare.getX() - 2, targetSquare.getY()), new Square(targetSquare.getX() + 1, targetSquare.getY()));
            }
        }

        if (targetSquare.getSquareType() == EN_PASSANT) {
            if (context.getBoard().getBoardMap().get(selectedSquare).getColor() == WHITE) {
                takePiece(new Square(targetSquare.getX(), targetSquare.getY() + 1));
            } else if (context.getBoard().getBoardMap().get(selectedSquare).getColor() == BLACK) {
                takePiece(new Square(targetSquare.getX(), targetSquare.getY() - 1));
            }
        }
    }

    /**
     * Moves the marked piece to the clicked square
     * <p>
     */
    private void makeMoves(Square moveFrom, Square moveTo) {
        if (context.getBoard().getBoardMap().containsKey(moveTo)) {
            takePiece(moveTo);
        }
        context.getBoard().markPieceOnSquareHasMoved(moveFrom);
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

    private void checkKingInCheck(ChessColor kingColor) {
        ChessColor opponentColor = (kingColor == WHITE) ? BLACK : WHITE;
        Square kingSquare = context.getBoard().fetchKingSquare(kingColor);
        MovementLogicUtil.isKingInCheck(context.getBoard(), kingSquare, opponentColor);
        if (kingSquare.getSquareType() == IN_CHECK)
            context.notifyKingInCheck(kingSquare.getX(), kingSquare.getY());
    }

    private boolean checkKingTaken() {
        for (IPiece p : context.getBoard().getDeadPieces()) {
            if (p.getPieceType().equals(KING)) return true;
        }
        return false;
    }

    /**
     * Checks if pawn a pawn is in a position to be promoted and initiates the promotion if so
     *
     * @param targetSquare
     */
    private boolean checkPawnPromotion(Square targetSquare) {
        if (targetSquare.getSquareType() == PROMOTION) {
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
