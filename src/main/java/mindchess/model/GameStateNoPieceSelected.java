package mindchess.model;

import mindchess.model.pieces.IPiece;
import mindchess.observers.GameStateObserver;

import java.util.ArrayList;
import java.util.List;

/**
 * The state which represent when no piece on the chess board has been selected, it will be the entry point for the state.
 *
 * From this state you can go to the piece selected state by inputting a coordinate for your own piece.
 *
 * Each state has to have a game context, a list of legal moves the current player can make, a list of plies to know what previous move has been made and a board.
 */
public class GameStateNoPieceSelected implements GameState {
    private IGameContext context;
    private List<GameStateObserver> gameStateObservers = new ArrayList<>();
    private List<Square> legalSquares;
    private List<Ply> plies;
    private Board board;

    GameStateNoPieceSelected(Board board, List<Ply> plies, List<Square> legalSquares, IGameContext context) {
        this.board = board;
        this.legalSquares = legalSquares;
        this.plies = plies;
        this.context = context;
    }

    /**
     * In no piece selected state a board input will select a piece of your own if you input the coordinates of the squares it is placed on.
     * @param x
     * @param y
     */
    @Override
    public void handleInput(int x, int y) {
        Square selectedSquare = new Square(x,y);
        if(SquareContainsAPiece(selectedSquare) && isPieceMyColor(selectedSquare)) {
            fetchLegalMoves(selectedSquare);
            if(legalSquares.size() == 0) return;

            context.setGameState(GameStateFactory.createGameStatePieceSelected(selectedSquare,board,plies,legalSquares,context));
            gameStateObservers.forEach(gameStateObserver -> context.addGameStateObserver(gameStateObserver));
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
     * @param selectedSquare
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
     * @param square
     * @return true if it contains a piece otherwise false
     */
    private boolean SquareContainsAPiece(Square square){
        if(board.isSquareContainsAPiece(square)) return true;
        return false;
    }

    /**
     * checks if a piece on a square is the same color as current player
     * @param square
     * @return true if colors match
     */
    private boolean isPieceMyColor(Square square){
        return board.pieceOnSquareColorEquals(square,context.getCurrentPlayerColor());
    }

    private void notifyDrawLegalMoves(){
        for (GameStateObserver gameStateObserver: gameStateObservers) {
            gameStateObserver.notifyDrawLegalMoves();
        }
    }


    @Override
    public String getGameStatus() {
        return "Game ongoing";
    }
    /**
     * In this state the game is always ongoing
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
