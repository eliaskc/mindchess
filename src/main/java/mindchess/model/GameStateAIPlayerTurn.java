package mindchess.model;

import mindchess.model.pieces.IPiece;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import static mindchess.model.ChessColor.*;

public class GameStateAIPlayerTurn implements GameState {
    private IGameContext context;
    private List<GameStateObserver> gameStateObservers;
    private List<Square> legalSquares;
    private List<Ply> plies;
    private Board board;

    GameStateAIPlayerTurn(Board board, List<Square> legalSquares, List<Ply> plies, IGameContext context, List<GameStateObserver> gameStateObservers) {
        this.board = board;
        this.legalSquares = legalSquares;
        this.plies = plies;
        this.context = context;
        this.gameStateObservers = gameStateObservers;
    }

    @Override
    public void handleInput(int x, int y) {
        List<Square> moveSquares = calculateMove();

        Square moveFrom = moveSquares.get(0);
        Square moveTo = moveSquares.get(1);


        Square selectedSquare = new Square(moveFrom.getX(), moveFrom.getY());

        context.setGameState(GameStateFactory.createGameStatePieceSelected(selectedSquare, board, plies, legalSquares, context));
        gameStateObservers.forEach(gameStateObserver -> context.addGameStateObserver(gameStateObserver));

        /*try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {

        }*/
        context.handleBoardInput(moveTo.getX(), moveTo.getY());
    }

    private List<Square> calculateMove() {
        var allBoardSquares = new ArrayList<Square>(board.getBoardMap().keySet());
        Random rand;

        var returnList = new ArrayList<Square>();

        while (true) {
            rand = new Random();
            Square randSquare = allBoardSquares.get(rand.nextInt(allBoardSquares.size()));
            IPiece randPiece = board.getBoardMap().get(randSquare);

            if (randPiece.getColor() == context.getCurrentPlayerColor()) {
                legalSquares = randPiece.getMoveDelegate().fetchMoves(board, randSquare, randPiece.getHasMoved(), true);
                if (legalSquares.size() > 0) {
                    returnList.add(randSquare);
                    break;
                }
            }
        }
        returnList.add(legalSquares.get(rand.nextInt(legalSquares.size())));
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
