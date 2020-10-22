package mindchess.model;

import mindchess.model.pieces.IPiece;
import mindchess.observers.EndGameObserver;
import mindchess.observers.GameObserver;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mindchess.model.PlayerType.*;

/**
 * Chess represents the model to the rest of the application
 * <p>
 * Delegates method calls from outside the model to the right part of the model
 */
public class ChessFacade {
    private final List<Game> gameList = new ArrayList<>();
    private Game currentGame;

    /**
     * sends the coordinates from the input to the current game to handle
     *
     * @param x the x coordinate from the input
     * @param y the y coordinate from the input
     */
    public void handleBoardInput(int x, int y) {
        currentGame.handleBoardInput(x, y);
    }

    /**
     * Forfeits the current game
     */
    public void forfeit() {
        currentGame.endGameAsForfeit();
    }

    /**
     * Draws the current game
     */
    public void acceptDraw() {
        currentGame.endGameAsDraw();
    }

    /**
     * Creates a new Game, makes it the current game, Initializes it and adds it to the game list
     */
    public void createNewGame(String whitePlayerName, String blackPlayerName, PlayerType whitePlayerType, PlayerType blackPlayerType, Integer gameLength) {
        currentGame = new Game();
        currentGame.initGame();
        currentGame.createPlayers(whitePlayerName, blackPlayerName, gameLength, whitePlayerType, blackPlayerType);
        gameList.add(currentGame);
    }

    //-------------------------------------------------------------------------------------
    //Timers
    public void initTimersInCurrentGame() {
        currentGame.initTimers();
    }

    public void stopAllTimers() {
        currentGame.stopAllTimers();
    }

    //-------------------------------------------------------------------------------------
    //Observers
    public void addGameObserverToCurrentGame(GameObserver gameObserver) {
        currentGame.addGameObserver(gameObserver);
    }

    public void addEndGameObserverToCurrentGame(EndGameObserver endgameObserver) {
        currentGame.addEndGameObserver(endgameObserver);
    }

    //-------------------------------------------------------------------------------------
    //Getters
    public ChessColor getCurrentPlayerColor() {
        return currentGame.getCurrentPlayerColor();
    }

    public Board getCurrentBoard() {
        return currentGame.getBoard();
    }

    public List<PieceType> getCurrentDeadPiecesByColor(ChessColor chessColor) {
        List<PieceType> pieceTypes = new ArrayList<>();
        for (IPiece piece : currentGame.getBoard().getDeadPieces()) {
            if (piece.getColor().equals(chessColor)) {
                pieceTypes.add(piece.getPieceType());
            }
        }
        return pieceTypes;
    }

    public Square getLastPlyMovedFromSquare() {
        return getCurrentGamePlies().get(getCurrentGamePlies().size() - 1).getMovedFrom();
    }

    public Square getLastPlyMovedToSquare() {
        return getCurrentGamePlies().get(getCurrentGamePlies().size() - 1).getMovedTo();
    }

    public int getCurrentWhiteTimerTime() {
        return currentGame.getPlayerWhiteTime();
    }

    public int getCurrentBlackTimerTime() {
        return currentGame.getPlayerBlackTime();
    }

    public String getCurrentPlayerName() {
        return currentGame.getCurrentPlayerName();
    }
  
    public List<String[]> getPlayersAndStatusInGameList(){
        ArrayList<String[]> returnList = new ArrayList<>();
        for (Game g : getGameList()) {
            String playerWhite = g.getPlayerWhite().getName();
            String playerBlack = g.getPlayerBlack().getName();
            String gameStatus = g.getGameStatus();
            String[] tempArray = {playerWhite,playerBlack,gameStatus};
            returnList.add(tempArray);
        }
        return returnList;
    }

    public String getCurrentWhitePlayerName() {
        return currentGame.getPlayerWhiteName();
    }

    public String getCurrentBlackPlayerName() {
        return currentGame.getPlayerBlackName();
    }

    public List<Ply> getCurrentGamePlies() {
        return new ArrayList<>(currentGame.getPlies());
    }

    //TODO How to remove cascading without adding dependancy on IPiece in Game
    public Map<Square, IPiece> getCurrentBoardMap() {
        return currentGame.getBoard().getBoardSnapShot();
    }

    private List<Game> getGameList() {
        return new ArrayList<>(gameList);
    }

    //TODO How to remove cascading without adding dependancy on IPiece in Game
    public List<IPiece> getCurrentDeadPieces() {
        return new ArrayList<>(currentGame.getBoard().getDeadPieces());
    }

    public List<Square> getCurrentLegalSquares() {
        return new ArrayList<>(currentGame.getLegalSquares());
    }


    public boolean isCurrentPlayerWhite() {
        return currentGame.getCurrentPlayer().equals(currentGame.getPlayerWhite());
    }

    //TODO How to remove cascading without adding dependancy on IPiece in Game
    public boolean isSquareOccupied(Square square) {
        return currentGame.getBoard().isAPieceOnSquare(square);
    }

    public boolean isGameOngoing() {
        return currentGame.isGameOngoing();
    }

    //-------------------------------------------------------------------------------------
    //Setters

    public void setIndexAsCurrentGame(int i){
        if(i >= gameList.size()){
            throw new IndexOutOfBoundsException();
        }
        currentGame = gameList.get(i);
    }

    public void setCurrentWhitePlayerTimerTime(int seconds) {
        currentGame.setPlayerWhiteTime(seconds);
    }

    public void setCurrentBlackPlayerTimerTime(int seconds) {
        currentGame.setPlayerBlackTime(seconds);
    }

    public void setCurrentPlayerWhiteName(String name) {
        currentGame.setPlayerWhiteName(name);
    }

    public void setCurrentPlayerBlackName(String name) {
        currentGame.setPlayerBlackName(name);
    }
}
