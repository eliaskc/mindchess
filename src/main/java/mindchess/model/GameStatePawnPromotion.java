package mindchess.model;

import mindchess.model.enums.PieceType;
import mindchess.model.pieces.IPiece;
import mindchess.model.pieces.PieceFactory;
import mindchess.observers.GameStateObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The state which represent when a pawn has reached the opposite side and is able to be promoted to a other piece.
 * <p>
 * From this state you can go to the no piece selected state by promoting the players pawn.
 * <p>
 * Each state has to have a game context, a list of legal moves the current player can make, a list of plies to know what previous move has been made and a board.
 * The pawn promotion state also needs a Square representing the square which the promotion will occur on.
 */
public class GameStatePawnPromotion implements GameState {

    private final Map<Square, PieceType> promotionPieces = new HashMap<>();

    private final IGameContext context;
    private final Square selectedSquare;
    private final List<GameStateObserver> gameStateObservers = new ArrayList<>();
    private final List<Square> legalSquares;
    private final List<Ply> plies;
    private final Board board;

    GameStatePawnPromotion(Square selectedSquare, Board board, List<Ply> plies, List<Square> legalSquares, IGameContext context) {
        this.selectedSquare = selectedSquare;
        this.board = board;
        this.legalSquares = legalSquares;
        this.plies = plies;
        this.context = context;
    }

    /**
     * The input corresponds to a value which can represent a piece type which the pawn can promote to
     * <p>
     * it will only promote when it recieves a valid input and when it does it will change player and state to no Piece selected for the next player
     *
     * @param x
     * @param y
     */
    @Override
    public void handleInput(int x, int y) {
        initPromotionPieces();
        Square selectedPromotion = new Square(x, y);
        if (promotionPieces.containsKey(selectedPromotion)) {
            promote(selectedSquare, selectedPromotion);
            notifySwitchPlayer();
            notifyDrawPieces();
            context.setGameState(GameStateFactory.createGameStateNoPieceSelected(board, plies, legalSquares, context));
            gameStateObservers.forEach(gameStateObserver -> context.addGameStateObserver(gameStateObserver));
        }
    }

    /**
     * Puts a piece with a corresponding square with a coded value in a map.
     * <p>
     * If the input from handle input matches the key it will return the value which is a piece type
     */
    private void initPromotionPieces() {
        promotionPieces.put(new Square(20, 0), PieceType.QUEEN);
        promotionPieces.put(new Square(21, 0), PieceType.KNIGHT);
        promotionPieces.put(new Square(22, 0), PieceType.ROOK);
        promotionPieces.put(new Square(23, 0), PieceType.BISHOP);
    }

    /**
     * The promotion happens by replacing the piece in the promotion square by a new Piece on the same square with a different type
     *
     * @param selectedSquare
     * @param selectedPromotion
     */
    private void promote(Square selectedSquare, Square selectedPromotion) {
        IPiece piece = null;
        try {
            piece = PieceFactory.createPiece(promotionPieces.get(selectedPromotion), context.getCurrentPlayerColor());
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid input");
        }
        board.placePieceOnSquare(selectedSquare, piece);
    }

    private void notifySwitchPlayer() {
        for (GameStateObserver gameStateObserver : gameStateObservers) {
            gameStateObserver.notifySwitchPlayer();
        }
    }

    private void notifyDrawPieces() {
        for (GameStateObserver gameStateObserver : gameStateObservers) {
            gameStateObserver.notifyDrawPieces();
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
