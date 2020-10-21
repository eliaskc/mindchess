package mindchess.model;

import mindchess.model.pieces.IPiece;
import mindchess.observers.EndGameObserver;
import mindchess.observers.GameObserver;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Chess represents the model to the rest of the application
 *
 * Delegates method calls from outside the model to the right part of the model
 *
 * It also makes sure that that the model updates when something happens during runtime
 */
public class ChessFacade {
    private final List<Game> gameList = new ArrayList<>();
    private Game currentGame;

    /**
     * sends the coordinates from the mouse click to the board to handle and notifies all observers a click has been made
     *
     * @param x the x coordinate for the mouse when it clicks
     * @param y the y coordinate for the mouse when it clicks
     */
    public void handleBoardInput(int x, int y) {
        currentGame.handleBoardInput(x, y);
    }

    public void forfeit() {
        currentGame.endGameAsForfeit();
    }

    public void acceptDraw() {
        currentGame.endGameAsDraw();
    }

    public void createNewGame() {
        currentGame = new Game();
        currentGame.initGame();
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

    public int getCurrentWhiteTimerTime() {
        return currentGame.getPlayerWhite().getCurrentTime();
    }

    public int getCurrentBlackTimerTime() {
        return currentGame.getPlayerBlack().getCurrentTime();
    }

    public String getCurrentPlayerName() {
        return currentGame.getCurrentPlayer().getName();
    }

    public String getCurrentPlayerBlackName() {
        return currentGame.getPlayerBlack().getName();
    }

    public String getCurrentPlayerWhiteName() {
        return currentGame.getPlayerWhite().getName();
    }

    //----------------------------Returns copies-----------------------------------------
    public List<Ply> getCurrentGamePlies() {
        return new ArrayList<>(currentGame.getPlies());
    }

    public Map<Square, IPiece> getCurrentBoardMap() {
        return new HashMap<>(currentGame.getBoard().getBoardMap());
    }

    public List<Game> getGameList() {
        return new ArrayList<>(gameList);
    }

    public List<IPiece> getCurrentDeadPieces() {
        return new ArrayList<>(currentGame.getBoard().getDeadPieces());
    }

    public List<Square> getCurrentLegalSquares() {
        return new ArrayList<>(currentGame.getLegalSquares());
    }


    public boolean isCurrentPlayerWhite() {
        return currentGame.getCurrentPlayer().equals(currentGame.getPlayerWhite());
    }

    public boolean isSquareOccupied(Square square){
        return currentGame.getBoard().getBoardMap().containsKey(square);
    }

    public boolean isGameOngoing() {
        return currentGame.isGameOngoing();
    }

    //-------------------------------------------------------------------------------------
    //Setters

    public void setCurrentWhitePlayerTimerTime(int seconds) {
        currentGame.getPlayerWhite().setTime(seconds);
    }

    public void setCurrentBlackPlayerTimerTime(int seconds) {
        currentGame.getPlayerBlack().setTime(seconds);
    }

    public void setCurrentPlayerWhiteName(String name) {
        currentGame.getPlayerWhite().setName(name);
    }

    public void setCurrentPlayerBlackName(String name) {
        currentGame.getPlayerBlack().setName(name);
    }
}
