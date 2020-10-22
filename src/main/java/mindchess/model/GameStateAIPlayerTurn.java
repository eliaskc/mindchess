package mindchess.model;

import mindchess.model.pieces.IPiece;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * A state which is used only when there is a CPU player in the game.
 *
 * If there is, this State "replaces" the No Piece Selected state.
 *
 * It calculates a move and then changes state to Piece Selected and thereafter makes the previously calculated move.
 */
public class GameStateAIPlayerTurn implements GameState {
    private final IGameContext context;
    private final List<GameStateObserver> gameStateObservers = new ArrayList<>();
    private final List<Square> legalSquares;
    private final List<Ply> plies;
    private final Board board;
    private final int difficulty;

    GameStateAIPlayerTurn(Board board, List<Square> legalSquares, List<Ply> plies, IGameContext context, int difficulty) {
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
    }

    /**
     * Calculates the move the CPU Player will make.
     *
     * @return the square to move from and the square to move to, in a list
     */
    private List<Square> calculateMove() {
        if (difficulty == 1)
            return calculateLevel1Move();
        /*else if (difficulty == 2)
            return calculateLevel2Move();*/
        throw new IllegalArgumentException();
    }

    /**
     * Finds a random move among the legal ones
     *
     * @return the square to move from and the square to move to, in a list
     */
    private List<Square> calculateLevel1Move() {
        var allBoardSquares = new ArrayList<>(board.getBoardMap().keySet());
        Random rand;

        var returnList = new ArrayList<Square>();
        List<Square> AILegalSquares;
        Square randToSquare = null;

        while (true) {
            rand = new Random();
            Square randSquare = allBoardSquares.get(rand.nextInt(allBoardSquares.size()));
            IPiece randPiece = board.getBoardMap().get(randSquare);

            if (randPiece.getColor() == context.getCurrentPlayerColor()) {
                AILegalSquares = randPiece.getMoveDelegate().fetchMoves(board, randSquare, randPiece.getHasMoved(), true);
                legalSquares.addAll(AILegalSquares);
                if (AILegalSquares.size() > 0) {
                    randToSquare = AILegalSquares.get(rand.nextInt(AILegalSquares.size()));
                    if (!(randToSquare.getSquareType() == SquareType.PROMOTION)) {
                        returnList.add(randSquare);
                        break;
                    }
                }
            }
        }

        returnList.add(randToSquare);
        return returnList;
    }

    /*private List<Square> calculateLevel2Move() {

    }*/

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
