package mindchess.model;

import mindchess.observers.EndGameObserver;
import mindchess.observers.GameObserver;
import mindchess.observers.TimerObserver;

import java.util.ArrayList;
import java.util.List;

import static mindchess.model.ChessColor.BLACK;
import static mindchess.model.ChessColor.WHITE;

public class Game implements TimerObserver, IGameContext, GameStateObserver {
    private final List<GameObserver> gameObservers = new ArrayList<>();
    private final List<EndGameObserver> endGameObservers = new ArrayList<>();

    private final Board board = new Board();
    //private final Map<Square, Piece> boardMap = board.getBoardMap(); //Representation of the relationship between square (squares) and pieces on the board

    private final List<Square> legalSquares = new ArrayList<>(); //List of squares that are legal to move to for the currently marked square
    private final List<Ply> plies = new ArrayList<>(); //A ply is the technical term for a player's move, and this is a list of moves

    private IPlayer playerWhite;
    private IPlayer playerBlack;
    private IPlayer currentPlayer;

    private GameState gameState;

    @Override
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    private void initGameStates() {
        gameState = GameStateFactory.createGameStateNoPieceSelected(board, plies, legalSquares, this);
        gameState.addGameStateObserver(this);
    }

    void initGame() {
        board.initBoard();
        initGameStates();
    }

    public void createPlayers(String whitePlayerName, String blackPlayerName, PlayerType whitePlayerType, PlayerType blackPlayerType, Integer gameLength) {
        if (whitePlayerName.equals("")) whitePlayerName = "White";
        if (blackPlayerName.equals("")) blackPlayerName = "Black";
        playerWhite = new Player(whitePlayerName, WHITE, whitePlayerType, gameLength);
        playerBlack = new Player(blackPlayerName, BLACK, blackPlayerType, gameLength);

        currentPlayer = playerWhite;
    }

    void initTimers() {
        playerWhite.getTimer().addObserver(this);
        playerBlack.getTimer().addObserver(this);
        playerWhite.getTimer().startTimer();
        playerWhite.getTimer().setActive(true);
        playerBlack.getTimer().startTimer();
    }

    /**
     * handleBoardClick() is the method responsible for investigating clicks on the board and deciding what should be done.
     * <p>
     * It receives input about the click and first fetches the clicked Square, and then the Piece on the Square (if there is one).
     * <p>
     * If no piece has been marked, it marks the Piece on the clicked Square
     * <p>
     * If a piece has been marked already, it checks if the clicked Square is one that is legal to move to and makes the move
     * if it is.
     *
     * @param x
     * @param y
     */
    public void handleBoardInput(int x, int y) {
        gameState.handleInput(x, y);
        if (!gameState.isGameOngoing()) {
            notifyEndGame();
        }
    }

    void switchPlayer() {
        currentPlayer.getTimer().setActive(false);
        currentPlayer = getOtherPlayer();
        currentPlayer.getTimer().setActive(true);

        if (currentPlayer.getPlayerType() == PlayerType.CPU) {
            gameState = GameStateFactory.createGameStateAIPlayerTurn(board, plies, legalSquares, this, this);
        }
        notifySwitchedPlayer();
    }

    private IPlayer getOtherPlayer() {
        if (currentPlayer == playerWhite) {
            return playerBlack;
        } else {
            return playerWhite;
        }
    }

    void endGameAsDraw() {
        setGameState(GameStateFactory.createGameStateGameOver("Game ended in draw"));
        stopAllTimers();
        notifyEndGame();
    }

    void endGameAsForfeit() {
        setGameState(GameStateFactory.createGameStateGameOver(getOtherPlayer().getName() + " has won the game"));
        stopAllTimers();
        notifyEndGame();
    }

    void stopAllTimers() {
        playerBlack.getTimer().stopTimer();
        playerWhite.getTimer().stopTimer();
    }

    @Override
    public void updateTimer() {
        notifyTimerUpdated();
    }

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

    public void notifyPawnPromotion() {
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.pawnPromotionSetup(getCurrentPlayerColor());
        }
    }

    @Override
    public void notifySwitchPlayer() {
        switchPlayer();
    }

    @Override
    public void addGameStateObserver(GameStateObserver gameStateObserver) {
        gameState.addGameStateObserver(gameStateObserver);
    }

    public ChessColor getCurrentPlayerColor() {
        return currentPlayer.getColor();
    }

    @Override
    public PlayerType getCurrentPlayerType() {
        return currentPlayer.getPlayerType();
    }

    public String getCurrentPlayerName() {
        return currentPlayer.getName();
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

    List<Square> getLegalSquares() {
        return legalSquares;
    }

    Board getBoard() {
        return board;
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


    boolean isGameOngoing() {
        return gameState.isGameOngoing();
    }
}
