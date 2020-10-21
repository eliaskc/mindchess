package mindchess.model;

import mindchess.model.pieces.IPiece;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

import static mindchess.model.ChessColor.*;
import static mindchess.model.PieceType.*;
import static mindchess.model.SquareType.*;

public class GameStatePieceSelected implements GameState {

    private Square selectedSquare;
    private IGameContext context;
    private IPiece takenPiece = null;
    private List<GameStateObserver> gameStateObservers = new ArrayList<>();;
    private List<Square> legalSquares;
    private List<Ply> plies;
    private Board board;

    GameStatePieceSelected(Square selectedSquare, Board board, List<Ply> plies, List<Square> legalSquares, IGameContext context) {
        this.selectedSquare = selectedSquare;
        this.board = board;
        this.legalSquares = legalSquares;
        this.plies = plies;
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
        if (board.getBoardMap().containsKey(targetSquare) && !targetSquare.equals(selectedSquare) && board.pieceOnSquareColorEquals(targetSquare,context.getCurrentPlayerColor())) {
            clearAndDrawLegalMoves();
            context.setGameState(GameStateFactory.createGameStateNoPieceSelected(board,plies,legalSquares,context));
            gameStateObservers.forEach(gameStateObserver -> context.addGameStateObserver(gameStateObserver));
            context.handleBoardInput(targetSquare.getX(), targetSquare.getY());
            return;
        }

        if (legalSquares.contains(targetSquare)) {
            targetSquare = getLegalSquareByCoordinates(x,y);
            move(selectedSquare,targetSquare);
            addMoveToPlies(selectedSquare, targetSquare);
            notifyDrawPieces();

            if (checkKingTaken()) {
                context.setGameState(GameStateFactory.createGameStateGameOver(context.getCurrentPlayerName() + " has won the game"));
                return;
            }

            if (checkPawnPromotion(targetSquare)) {
                context.setGameState(GameStateFactory.createGameStatePawnPromotion(targetSquare,board,plies,legalSquares,context));
                gameStateObservers.forEach(gameStateObserver -> context.addGameStateObserver(gameStateObserver));
                clearAndDrawLegalMoves();
                return;
            }

            notifySwitchPlayer();
            checkKingInCheck(context.getCurrentPlayerColor());
        }
        context.setGameState(GameStateFactory.createGameStateNoPieceSelected(board,plies,legalSquares,context));
        gameStateObservers.forEach(gameStateObserver -> context.addGameStateObserver(gameStateObserver));
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
        if (!board.getBoardMap().containsKey(selectedSquare)) return;

        //castling
        if (targetSquare.getSquareType() == CASTLING) {
            if (targetSquare.getX() > selectedSquare.getX()) {
                makeMoves(new Square(targetSquare.getX() + 1, targetSquare.getY()), new Square(targetSquare.getX() - 1, targetSquare.getY()));
            } else if (targetSquare.getX() < selectedSquare.getX()) {
                makeMoves(new Square(targetSquare.getX() - 2, targetSquare.getY()), new Square(targetSquare.getX() + 1, targetSquare.getY()));
            }
        }

        if (targetSquare.getSquareType() == EN_PASSANT) {
            if (board.pieceOnSquareColorEquals(selectedSquare, WHITE)) {
                takePiece(new Square(targetSquare.getX(), targetSquare.getY() + 1));
            } else if (board.pieceOnSquareColorEquals(selectedSquare, BLACK)) {
                takePiece(new Square(targetSquare.getX(), targetSquare.getY() - 1));
            }
        }
    }

    /**
     * Moves the marked piece to the clicked square
     * <p>
     */
    private void makeMoves(Square moveFrom, Square moveTo) {
        if (board.isSquareAPiece(moveTo)) {
            takePiece(moveTo);
        }
        board.markPieceOnSquareHasMoved(moveFrom);
        board.getBoardMap().put(moveTo, board.getBoardMap().get(moveFrom));
        board.getBoardMap().remove(moveFrom);
    }

    private void clearAndDrawLegalMoves(){
        legalSquares.clear();
        notifyDrawLegalMoves();
    }

    private void takePiece(Square pieceOnSquareToTake) {
        takenPiece = board.getBoardMap().remove(pieceOnSquareToTake);
        board.getDeadPieces().add(takenPiece);
        notifyDrawDeadPieces();
    }

    private void checkKingInCheck(ChessColor kingColor) {
        ChessColor opponentColor = (kingColor == WHITE) ? BLACK : WHITE;
        Square kingSquare = board.fetchKingSquare(kingColor);

        MovementLogicUtil.isKingInCheck(board, kingSquare, opponentColor);
        if (kingSquare.getSquareType() == IN_CHECK)
            notifyKingInCheck(kingSquare.getX(), kingSquare.getY());
    }

    private boolean checkKingTaken() {
        for (IPiece p : board.getDeadPieces()) {
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
            notifyPawnPromotion();
            return true;
        }
        return false;
    }

    private void addMoveToPlies(Square selectedSquare, Square targetSquare) {
        Ply ply = new Ply(context.getCurrentPlayerName(), selectedSquare, targetSquare, board.getBoardMap().get(targetSquare), takenPiece, board.getBoardMap());
        plies.add(ply);
    }

    private Square getLegalSquareByCoordinates(int x, int y) {
        for (Square s : legalSquares) {
            if (s.getX() == x && s.getY() == y)
                return s;
        }
        throw new NoSuchElementException("No legal square with matching coordinates found");
    }

    private void notifyPawnPromotion(){
        for (GameStateObserver gameStateObserver: gameStateObservers) {
            gameStateObserver.notifyPawnPromotion();
        }
    }

    private void notifyDrawPieces(){
        for (GameStateObserver gameStateObserver: gameStateObservers) {
            gameStateObserver.notifyDrawPieces();
        }
    }

    private void notifyDrawDeadPieces(){
        for (GameStateObserver gameStateObserver: gameStateObservers) {
            gameStateObserver.notifyDrawDeadPieces();
        }
    }

    private void notifySwitchPlayer(){
        for (GameStateObserver gameStateObserver: gameStateObservers) {
            gameStateObserver.notifySwitchPlayer();
        }
    }

    private void notifyDrawLegalMoves(){
        for (GameStateObserver gameStateObserver: gameStateObservers) {
            gameStateObserver.notifyDrawLegalMoves();
        }
    }

    private void notifyKingInCheck(int x, int y){
        for (GameStateObserver gameStateObserver: gameStateObservers) {
            gameStateObserver.notifyKingInCheck(x,y);
        }
    }


    @Override
    public String getGameStatus() {
        return "Game ongoing";
    }

    @Override
    public boolean isGameOngoing() {
        return true;
    }

    @Override
    public void addGameStateObserver(GameStateObserver gameStateObserver) {
        gameStateObservers.add(gameStateObserver);
    }
}
