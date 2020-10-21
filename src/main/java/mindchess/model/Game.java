package mindchess.model;

import mindchess.observers.EndGameObserver;
import mindchess.observers.GameObserver;
import mindchess.observers.TimerObserver;

import java.util.ArrayList;
import java.util.List;

import static mindchess.model.ChessColor.BLACK;
import static mindchess.model.ChessColor.WHITE;

/**
 * The game class represents one game which has a board and two players. It keeps track of legal squares,current game state, plies and who
 * is the current player.
 */
public class Game implements TimerObserver,IGameContext, GameStateObserver {
    private final List<GameObserver> gameObservers = new ArrayList<>();
    private final List<EndGameObserver> endGameObservers = new ArrayList<>();

    private final Board board = new Board();

    private final List<Square> legalSquares = new ArrayList<>(); //List of squares that are legal to move to for the currently marked square
    private final List<Ply> plies = new ArrayList<>(); //A ply is the technical term for a player's move, and this is a list of moves

    private final Player playerWhite = new Player("Player 1", WHITE);
    private final Player playerBlack = new Player("Player 2", BLACK);
    private Player currentPlayer;

    private GameState gameState;

    /**
     * handleBoardInput() is the method responsible for handling a input on the chess board.
     *
     * It delegates the input to the handleInput() method of the current state who then decides what to do with it.
     *
     * If the game has ended does it notify the rest of the program that it has.
     * @param x The x value of where on the application the input happend
     * @param y The y value of where on the application the input happend
     */
    public void handleBoardInput(int x, int y) {
        gameState.handleInput(x, y);
        if (!gameState.isGameOngoing()) {
            notifyEndGame();
        }
    }

    /**
     * Switches the current active player and switches whos timers is active.
     *
     * Notifies that the player has switched.
     */
    void switchPlayer() {
        currentPlayer.setTimerActive(false);
        currentPlayer = getOtherPlayer();
        currentPlayer.setTimerActive(true);
        notifySwitchedPlayer();
    }

    /**
     * sets the state of the game to game over, with the gamestatus string of gameover state set to the "game ended in a draw"
     *
     * stops both players timers, notifies that the game has ended.
     */
    void endGameAsDraw() {
        setGameState(GameStateFactory.createGameStateGameOver("Game ended in draw"));
        stopAllTimers();
        notifyEndGame();
    }

    /**
     * sets the state of the game to game over, with the gamestatus string of gameover state set to the opponents name + "has won the game"
     *
     * stops both players timers, notifies that the game has ended.
     */
    void endGameAsForfeit() {
        setGameState(GameStateFactory.createGameStateGameOver(getOtherPlayer().getName() + " has won the game"));
        stopAllTimers();
        notifyEndGame();
    }

    //-------------------------------------------------------------------------------------
    //Initializes

    /**
     * sets the first state to a no piece selected state and adds the game as an observer, an entry into the states
     */
    private void initGameStates() {
        gameState = GameStateFactory.createGameStateNoPieceSelected(board,plies,legalSquares,this);
        addGameStateObserver(this);
    }

    /**
     * initializes the board and starts the current player as white according to chess rules
     */
    void initGame() {
        board.initBoard();
        currentPlayer = playerWhite;
        initGameStates();
    }

    /**
     * initializes the players timers and adds game as observer.
     *
     * starts the white timer as it's always the first to move in a new game.
     */
    void initTimers() {
        playerWhite.addTimerObserver(this);
        playerBlack.addTimerObserver(this);
        playerWhite.startPlayerTimer();
        playerWhite.setTimerActive(true);
        playerBlack.startPlayerTimer();
    }

    //-------------------------------------------------------------------------------------
    //Timers
    void stopAllTimers() {
        playerBlack.stopPlayerTimer();
        playerWhite.stopPlayerTimer();
    }

    @Override
    public void updateTimer() {
        notifyTimerUpdated();
    }

    //-------------------------------------------------------------------------------------
    //Notify
    private void notifyTimerUpdated() {
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.updateTimer();
        }
    }

    public void notifyEndGame() {
        for (EndGameObserver p : endGameObservers) {
            p.showEndGameResult(gameState.getGameStatus());
        }
    }

    @Override
    public void notifyTimerEnded() {
        switchPlayer();
        setGameState(GameStateFactory.createGameStateGameOver(currentPlayer.getName() + " has won the game"));
        stopAllTimers();
        notifyEndGame();
    }

    public void notifyDrawPieces() {
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.drawPieces();
        }
    }

    public void notifyDrawDeadPieces() {
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.drawDeadPieces();
        }
    }

    public void notifyDrawLegalMoves() {
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.drawLegalMoves();
        }
    }

    public void notifyKingInCheck(int x, int y) {
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.kingInCheck(x, y);
        }
    }

    public void notifySwitchedPlayer() {
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.switchedPlayer();
        }
    }

    public void notifyPawnPromotion() {
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.pawnPromotionSetup(getCurrentPlayerColor());
        }
    }

    @Override
    public void notifySwitchPlayer() {
        switchPlayer();
    }

    //-------------------------------------------------------------------------------------
    //Observers
    @Override
    public void addGameStateObserver(GameStateObserver gameStateObserver) {
        gameState.addGameStateObserver(gameStateObserver);
    }

    void addGameObserver(GameObserver gameObserver) {
        gameObservers.add(gameObserver);
    }

    void removeGameObserver(GameObserver gameObserver) {
        gameObservers.remove(gameObserver);
    }

    void addEndGameObserver(EndGameObserver endgameObserver) {
        endGameObservers.add(endgameObserver);
    }

    void removeEndGameObserver(EndGameObserver endgameObserver) {
        endGameObservers.remove(endgameObserver);
    }

    //-------------------------------------------------------------------------------------
    //Getters
    public ChessColor getCurrentPlayerColor() {
        return currentPlayer.getColor();
    }

    public String getCurrentPlayerName() {
        return currentPlayer.getName();
    }

    List<Square> getLegalSquares() {
        return legalSquares;
    }

    Board getBoard() {
        return board;
    }

    Player getPlayerWhite() {
        return playerWhite;
    }

    Player getPlayerBlack() {
        return playerBlack;
    }

    Player getCurrentPlayer() {
        return currentPlayer;
    }

    List<Ply> getPlies() {
        return plies;
    }

    int getPlayerWhiteTime(){
        return playerWhite.getCurrentTime();
    }

    int getPlayerBlackTime(){
        return playerBlack.getCurrentTime();
    }

    String getPlayerWhiteName(){
        return playerWhite.getName();
    }

    String getPlayerBlackName(){
        return playerBlack.getName();
    }

    private Player getOtherPlayer() {
        if (currentPlayer == playerWhite) {
            return playerBlack;
        } else {
            return playerWhite;
        }
    }


    boolean isGameOngoing() {
        return gameState.isGameOngoing();
    }

    //-------------------------------------------------------------------------------------
    //Setters

    /**
     * Sets the gamestate, called from the states themselves to decide which next state should be
     * @param gameState the new game state
     */
    @Override
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    void setPlayerWhiteTime(int seconds){
        playerWhite.setTime(seconds);
    }

    void setPlayerBlackTime(int seconds){
        playerBlack.setTime(seconds);
    }

    void setPlayerWhiteName(String name){
        playerWhite.setName(name);
    }

    void setPlayerBlackName(String name){
        playerBlack.setName(name);
    }
}
