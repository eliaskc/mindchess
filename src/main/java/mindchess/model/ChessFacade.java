package mindchess.model;

import mindchess.model.pieces.IPiece;
import mindchess.observers.EndGameObserver;
import mindchess.observers.GameObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static mindchess.model.PlayerType.*;

/**
 * Chess represents the model to the rest of the application
 * <p>
 * It also makes sure that that the model updates when something happens during runtime
 * <p>
 * (Composite pattern?)
 */
public class ChessFacade {
    private final List<Game> gameList = new ArrayList<>();
    private Game currentGame;

    public String getCurrentPlayerName() {
        return currentGame.getCurrentPlayer().getName();
    }

    public String getCurrentPlayerBlackName() {
        return currentGame.getPlayerBlack().getName();
    }

    public String getCurrentPlayerWhiteName() {
        return currentGame.getPlayerWhite().getName();
    }

    public boolean isCurrentPlayerWhite() {
        return currentGame.getCurrentPlayer().equals(currentGame.getPlayerWhite());
    }

    public boolean isSquareOccupied(Square square){
        return currentGame.getBoard().getBoardMap().containsKey(square);
    }

    public void forfeit() {
        currentGame.endGameAsForfeit();
    }

    public void acceptDraw() {
        currentGame.endGameAsDraw();
    }

    public void addGameObserverToCurrentGame(GameObserver gameObserver) {
        currentGame.addGameObserver(gameObserver);
    }

    public void addEndGameObserverToCurrentGame(EndGameObserver endgameObserver) {
        currentGame.addEndGameObserver(endgameObserver);
    }

    public void initTimersInCurrentGame() {
        currentGame.initTimers();
    }

    public ChessColor getCurrentPlayerColor() {
        return currentGame.getCurrentPlayerColor();
    }

    //----------------------------Returns copies-----------------------------------------
    public List<Ply> getCurrentGamePlies() {
        return new ArrayList<>(currentGame.getPlies());
    }

    public Map<Square, IPiece> getCurrentBoardMap() {
        return new HashMap<>(currentGame.getBoard().getBoardMap());
    }

    public Board getCurrentBoard() {
        return currentGame.getBoard();
    }

    public List<Game> getGameList() {
        return new ArrayList<>(gameList);
    }
    public List<IPiece> getCurrentDeadPieces() {
        return new ArrayList<>(currentGame.getBoard().getDeadPieces());
    }

    public List<PieceType> getCurrentDeadPiecesByColor(ChessColor chessColor){
        List<PieceType> pieceTypes = new ArrayList<>();
        for (IPiece piece : currentGame.getBoard().getDeadPieces()){
            if (piece.getColor().equals(chessColor)){
                pieceTypes.add(piece.getPieceType());
            }
        }
        return pieceTypes;
    }

    public Square getLastPlyMovedFromSquare(){
        return getCurrentGamePlies().get(getCurrentGamePlies().size() - 1).getMovedFrom();
    }

    public Square getLastPlyMovedToSquare(){
        return getCurrentGamePlies().get(getCurrentGamePlies().size() - 1).getMovedTo();
    }

    public List<Square> getCurrentLegalSquares() {
        return new ArrayList<>(currentGame.getLegalSquares());
    }

    public int getCurrentWhiteTimerTime() {
        return currentGame.getPlayerWhite().getCurrentTime();
    }

    public int getCurrentBlackTimerTime() {
        return currentGame.getPlayerBlack().getCurrentTime();
    }


    /**
     * sends the coordinates from the mouse click to the board to handle and notifies all observers a click has been made
     *
     * @param x the x coordinate for the mouse when it clicks
     * @param y the y coordinate for the mouse when it clicks
     */
    public void handleBoardInput(int x, int y) {
        currentGame.handleBoardInput(x, y);
    }

    //-------------------------------------------------------------------------------------
    //Game

    public void createNewGame(String whitePlayerName, String blackPlayerName, PlayerType whitePlayerType, PlayerType blackPlayerType, Integer gameLength) {
        currentGame = new Game();
        currentGame.initGame();
        currentGame.createPlayers(whitePlayerName, blackPlayerName, whitePlayerType, blackPlayerType, gameLength);
        gameList.add(currentGame);
    }

    public boolean isGameOngoing() {
        return currentGame.isGameOngoing();
    }

    public void stopAllTimers() {
        currentGame.stopAllTimers();
    }
}
