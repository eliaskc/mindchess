package chess.model;

import chess.observers.EndGameObserver;
import chess.observers.GameObserver;
import chess.observers.TimerObserver;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static chess.model.ChessColor.BLACK;
import static chess.model.ChessColor.WHITE;

public class Game implements TimerObserver {
    private final List<GameObserver> gameObservers = new ArrayList<>();
    private final List<EndGameObserver> endGameObservers = new ArrayList<>();

    private final Board board = new Board();
    //private final Map<Point, Piece> boardMap = board.getBoardMap(); //Representation of the relationship between points (squares) and pieces on the board

    private final List<Point> legalPoints = new ArrayList<>(); //List of points that are legal to move to for the currently marked point
    private final List<Ply> plies = new ArrayList<>(); //A ply is the technical term for a player's move, and this is a list of moves

    private final Player playerWhite = new Player("Player 1", WHITE);
    private final Player playerBlack = new Player("Player 2", BLACK);
    private Player currentPlayer;

    private GameState gameState;

    void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    private void initGameStates(){
        gameState = GameStateFactory.createNoPieceSelectedState(this);
    }

    void initGame() {
        board.initBoard();
        currentPlayer = playerWhite;
        initGameStates();
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
     * It receives input about the click and first fetches the clicked Point, and then the Piece on the point (if there is one).
     * <p>
     * If no piece has been marked, it marks the Piece on the clicked Point
     * <p>
     * If a piece has been marked already, it checks if the clicked Point is one that is legal to move to and makes the move
     * if it is.
     *
     * @param x
     * @param y
     */
    void handleBoardInput(int x, int y) {
        gameState.handleInput(x,y);
        if (!gameState.isGameOngoing()) {
            notifyEndGame();
        }
    }

    void switchPlayer() {
        currentPlayer.getTimer().setActive(false);
        currentPlayer = getOtherPlayer();
        currentPlayer.getTimer().setActive(true);
        notifySwitchedPlayer();
    }

    private Player getOtherPlayer(){
        if (currentPlayer == playerWhite) {
            return playerBlack;
        } else {
            return playerWhite;
        }
    }

    void endGameAsDraw(){
        setGameState(GameStateFactory.createGameOverState("Game ended in draw", this));
        stopAllTimers();
        notifyEndGame();
    }

    void endGameAsForfeit(){
        setGameState(GameStateFactory.createGameOverState(getOtherPlayer().getName() + " has won the game",this));
        stopAllTimers();
        notifyEndGame();
    }

    void stopAllTimers(){
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
        setGameState(GameStateFactory.createGameOverState(currentPlayer.getName() + " has won the game",this));
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
            gameObserver.pawnPromotionSetup(currentPlayer.getColor());
        }
    }

    ChessColor getCurrentPlayerColor() {
        return currentPlayer.getColor();
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

    List<Point> getLegalPoints() {
        return legalPoints;
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

    //public becouse of tests
    public GameState getGameState() {
        return gameState;
    }
}
