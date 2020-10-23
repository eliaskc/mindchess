package mindchess.model.gameStates;

import mindchess.model.*;
import mindchess.model.pieces.IPiece;
import mindchess.observers.GameStateObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * The state which represent when no piece on the chess board has been selected, it will be the entry point for the state.
 * <p>
 * From this state you can go to the piece selected state by inputting a coordinate for your own piece.
 * <p>
 * Each state has to have a game context, a list of legal moves the current player can make, a list of plies to know what previous move has been made and a board.
 *
 * @author Erik Wessman, Elias Carlson, Elias Hallberg, Arvid Holmqvist
 */
public class GameStateNoPieceSelected implements GameState {
    private final IGameContext context;
    private final List<GameStateObserver> gameStateObservers = new ArrayList<>();
    private final List<Square> legalSquares;
    private final List<Ply> plies;
    private final IBoard board;

    GameStateNoPieceSelected(IBoard board, List<Ply> plies, List<Square> legalSquares, IGameContext context) {
        this.board = board;
        this.legalSquares = legalSquares;
        this.plies = plies;
        this.context = context;
    }

    /**
     * In no piece selected state a board input will select a piece of your own if you input the coordinates of the squares it is placed on.
     *
     * @param x the horizontal chess coordinate of the input
     * @param y the vertical chess coordinate of the input
     */
    @Override
    public void handleInput(int x, int y) {
        Square selectedSquare = new Square(x, y);
        if (SquareContainsAPiece(selectedSquare) && isPieceMyColor(selectedSquare)) {
            fetchLegalMoves(selectedSquare);
            if (legalSquares.size() == 0) return;

            context.setGameState(GameStateFactory.createGameStatePieceSelected(selectedSquare, board, plies, legalSquares, context));
            gameStateObservers.forEach(context::addGameStateObserver);
            notifyDrawLegalMoves();
        }
    }

    /**
     * Adds all legal squares the marked piece can move to to the legalSquares list
     */
    private void fetchLegalMoves(Square selectedSquare) {
        IPiece pieceToCheck = board.fetchPieceOnSquare(selectedSquare);
        legalSquares.addAll(pieceToCheck.getMoveDelegate().fetchMoves(board, selectedSquare, pieceToCheck.getHasMoved(), true));
        legalSquares.addAll(getEnPassantSquares(selectedSquare));
    }

    /**
     * fetches the squares possible if any for the special move of "en passant"
     *
     * @param selectedSquare the currently selected square
     * @return a list of possible en passant squares
     */
    private List<Square> getEnPassantSquares(Square selectedSquare) {
        List<Square> enPassantPoints = new ArrayList<>();
        if (plies.size() == 0) return enPassantPoints;

        Ply lastPly = plies.get(plies.size() - 1);

        return MovementLogicUtil.getEnPassantSquares(lastPly, selectedSquare, board);
    }

    /**
     * checks if a square contains a piece
     *
     * @param square the square to check
     * @return true if it contains a piece otherwise false
     */
    private boolean SquareContainsAPiece(Square square) {
        return board.isSquareContainsAPiece(square);
    }

    /**
     * checks if a piece on a square is the same color as current player
     *
     * @param square the square with the piece to check
     * @return true if colors match
     */
    private boolean isPieceMyColor(Square square) {
        return board.pieceOnSquareColorEquals(square, context.getCurrentPlayerColor());
    }

    private void notifyDrawLegalMoves() {
        for (GameStateObserver gameStateObserver : gameStateObservers) {
            gameStateObserver.notifyDrawLegalMoves();
        }
    }


    @Override
    public String getGameStatus() {
        return "Game ongoing";
    }

    /**
     * In this state the game is always ongoing
     *
     * @return true
     */
    @Override
    public boolean isGameOngoing() {
        return true;
    }

    @Override
    public void addGameStateObserver(GameStateObserver gameStateObserver) {
        gameStateObservers.add(gameStateObserver);
    }
}
