package mindchess.model.gameStates;

import mindchess.model.enums.SquareType;
import mindchess.model.*;
import mindchess.model.pieces.IPiece;
import mindchess.observers.GameStateObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A state which is used only when there is a CPU player in the game.
 * <p>
 * If there is, this State "replaces" the No Piece Selected state.
 * <p>
 * It calculates a move and then changes state to Piece Selected and thereafter makes the previously calculated move.
 */
public class GameStateAIPlayerTurn implements GameState {
    private final IGameContext context;
    private final List<GameStateObserver> gameStateObservers = new ArrayList<>();
    private final List<Square> legalSquares;
    private final List<Ply> plies;
    private final IBoard board;
    private final int difficulty;
    private boolean pawnPromotionMove;

    GameStateAIPlayerTurn(IBoard board, List<Square> legalSquares, List<Ply> plies, IGameContext context, int difficulty) {
        this.board = board;
        this.legalSquares = legalSquares;
        this.plies = plies;
        this.context = context;
        this.difficulty = difficulty;
    }

    @Override
    public void handleInput(int x, int y) {


        List<Square> moveSquares = calculateMove();

        Square moveFrom = moveSquares.get(0);
        Square moveTo = moveSquares.get(1);

        Square selectedSquare = new Square(moveFrom.getX(), moveFrom.getY());

        context.setGameState(GameStateFactory.createGameStatePieceSelected(selectedSquare, board, plies, legalSquares, context));
        gameStateObservers.forEach(context::addGameStateObserver);

        context.handleBoardInput(moveTo.getX(), moveTo.getY());

        if (pawnPromotionMove) {
            context.handleBoardInput(20, 0);
        }
    }

    /**
     * Calculates the move the CPU Player will make.
     *
     * @return the square to move from and the square to move to, in a list
     */
    private List<Square> calculateMove() {
        if (difficulty == 1)
            return calculateLevel1Move();
        else if (difficulty == 2)
            return calculateLevel2Move();
        throw new IllegalArgumentException();
    }

    /**
     * Finds a random move among the legal ones for the AI player
     *
     * @return a list with the Squares to move from and to
     */
    private List<Square> calculateLevel1Move() {
        var allBoardSquares = new ArrayList<>(board.getBoardKeys());
        Random rand;

        var returnList = new ArrayList<Square>();
        List<Square> AILegalSquares;
        Square moveTo;

        while (true) {
            rand = new Random();
            Square moveFrom = allBoardSquares.get(rand.nextInt(allBoardSquares.size()));
            IPiece piece = board.getPieceOnSquare(moveFrom);

            if (piece.getColor() == context.getCurrentPlayerColor()) {
                AILegalSquares = piece.getMoveDelegate().fetchMoves(board, moveFrom, piece.getHasMoved(), true);
                legalSquares.addAll(AILegalSquares);
                if (AILegalSquares.size() > 0) {
                    moveTo = AILegalSquares.get(rand.nextInt(AILegalSquares.size()));
                    if (!(moveTo.getSquareType() == SquareType.PROMOTION)) {
                        returnList.add(moveFrom);
                        break;
                    }
                }
            }
        }

        returnList.add(moveTo);
        return returnList;
    }

    /**
     * Finds a move for the AI to make.
     *   - If there are pieces that the AI could take, it takes the highest valued one
     *   - If not, a move is randomized
     * @return a list with the Squares to move from and to
     */
    private List<Square> calculateLevel2Move() {
        var returnList = new ArrayList<Square>();
        var allBoardSquares = new ArrayList<>(board.getBoardKeys());
        List<Square> AILegalSquares;
        int maxValue = 0;
        Square moveTo = null;
        Square moveFrom = null;
        Random rand = new Random();

        for (Square possibleMoveFrom : allBoardSquares) {
            IPiece piece = board.getPieceOnSquare(possibleMoveFrom);
            if (piece.getColor() == context.getCurrentPlayerColor()) {
                AILegalSquares = piece.getMoveDelegate().fetchMoves(board, possibleMoveFrom, piece.getHasMoved(), true);
                legalSquares.addAll(AILegalSquares);
                for (Square possibleMoveTo : AILegalSquares) {
                    if (board.isAPieceOnSquare(possibleMoveTo)) {
                        int currentPieceValue = board.getPieceOnSquare(possibleMoveTo).getPieceValue();
                        if (currentPieceValue > maxValue) {
                            maxValue = currentPieceValue;
                            moveTo = possibleMoveTo;
                            moveFrom = possibleMoveFrom;
                        }
                    }
                }
                if (maxValue == 0 && AILegalSquares.size() != 0) {
                    moveFrom = possibleMoveFrom;
                    moveTo = AILegalSquares.get(rand.nextInt(AILegalSquares.size()));
                }
            }
        }

        assert moveTo != null;
        pawnPromotionMove = moveTo.getSquareType().equals(SquareType.PROMOTION);

        returnList.add(moveFrom);
        returnList.add(moveTo);

        return returnList;
    }

    @Override
    public String getGameStatus() {
        return "AI Player calculating move";
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
