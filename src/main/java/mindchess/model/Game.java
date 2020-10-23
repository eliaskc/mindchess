package mindchess.model;

import mindchess.model.enums.ChessColor;
import mindchess.model.enums.PlayerType;
import mindchess.model.gameStates.GameState;
import mindchess.model.gameStates.GameStateFactory;
import mindchess.observers.EndGameObserver;
import mindchess.observers.GameObserver;
import mindchess.observers.GameStateObserver;
import mindchess.observers.TimerObserver;

import java.util.ArrayList;
import java.util.List;

import static mindchess.model.enums.ChessColor.BLACK;
import static mindchess.model.enums.ChessColor.WHITE;
import static mindchess.model.enums.PlayerType.*;

/**
 * The game class represents one game which has a board and two players. It keeps track of legal squares,current game state, plies and who
 * is the current player.
 */
public class Game implements TimerObserver, IGameContext, GameStateObserver {
    private final List<GameObserver> gameObservers = new ArrayList<>();
    private final List<EndGameObserver> endGameObservers = new ArrayList<>();

    private final IBoard board = new Board();

    private final List<Square> legalSquares = new ArrayList<>(); //List of squares that are legal to move to for the currently marked square
    private final List<Ply> plies = new ArrayList<>(); //A ply is the technical term for a player's move, and this is a list of moves

    private IPlayer playerWhite;
    private IPlayer playerBlack;
    private IPlayer currentPlayer;

    private GameState gameState;

    /**
     * Initializes the players for the game
     * @param whitePlayerName the name for the player with the white pieces
     * @param blackPlayerName the name for the player with the black pieces
     * @param gameLength the length for each of the players' timers
     * @param whitePlayerType the PlayerType of the player with the white pieces
     * @param blackPlayerType the PlayerType of the player with the black pieces
     */
    public void createPlayers(String whitePlayerName, String blackPlayerName, Integer gameLength, PlayerType whitePlayerType, PlayerType blackPlayerType) {
        if (whitePlayerName.equals("")) whitePlayerName = "White";
        if (blackPlayerName.equals("")) blackPlayerName = "Black";
        playerWhite = new Player(whitePlayerName, WHITE, whitePlayerType, gameLength);
        playerBlack = new Player(blackPlayerName, BLACK, blackPlayerType, gameLength);

        currentPlayer = playerWhite;
    }

    /**
     * handleBoardInput() is the method responsible for handling a input on the chess board.
     * <p>
     * It delegates the input to the handleInput() method of the current state who then decides what to do with it.
     * <p>
     * If the game has ended does it notify the rest of the program that it has.
     *
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
     * <p>
     * Notifies that the player has switched.
     */
    void switchPlayer() {
        currentPlayer.setTimerActive(false);
        currentPlayer = getOtherPlayer();
        currentPlayer.setTimerActive(true);

        if (currentPlayer.getPlayerType() == CPU_LEVEL1)
            gameState = GameStateFactory.createGameStateAIPlayerTurn(board, plies, legalSquares, this, this, 1);
        else if (currentPlayer.getPlayerType() == CPU_LEVEL2)
            gameState = GameStateFactory.createGameStateAIPlayerTurn(board, plies, legalSquares, this, this, 2);

        currentPlayer.setTimerActive(true);
        notifySwitchedPlayer();
    }

    /**
     * sets the state of the game to game over, with the gamestatus string of gameover state set to the "game ended in a draw"
     * <p>
     * stops both players timers, notifies that the game has ended.
     */
    void endGameAsDraw() {
        setGameState(GameStateFactory.createGameStateGameOver("Game ended in draw"));
        stopAllTimers();
        notifyEndGame();
    }

    /**
     * sets the state of the game to game over, with the gamestatus string of gameover state set to the opponents name + "has won the game"
     * <p>
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
        gameState = GameStateFactory.createGameStateNoPieceSelected(board, plies, legalSquares, this);
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
     * <p>
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

    @Override
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

    public void notifyPawnPromotionSetup() {
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.pawnPromotionSetup(getCurrentPlayerColor());
        }
    }

    public void notifyPawnPromotionCleanUp(){
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.pawnPromotionCleanUp();
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

    public PlayerType getCurrentPlayerType() {
        return currentPlayer.getPlayerType();
    }

    List<Square> getLegalSquares() {
        return legalSquares;
    }

    IBoard getBoard() {
        return board;
    }
  
    public String getGameStatus(){
      return gameState.getGameStatus();
    }

    IPlayer getPlayerWhite() {
        return playerWhite;
    }

    IPlayer getPlayerBlack() {
        return playerBlack;
    }

    IPlayer getCurrentPlayer() {
        return currentPlayer;
    }

    List<Ply> getPlies() {
        return plies;
    }

    int getPlayerWhiteTime() {
        return playerWhite.getCurrentTime();
    }

    int getPlayerBlackTime() {
        return playerBlack.getCurrentTime();
    }
  
    String getPlayerBlackName() {
        return playerBlack.getName();
    }

    String getPlayerWhiteName() {
        return playerWhite.getName();
    }
  
    private IPlayer getOtherPlayer() {
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
     *
     * @param gameState the new game state
     */
    @Override
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
