package chess.model;

import chess.observers.EndGameObserver;
import chess.observers.GameObserver;
import chess.model.GameState.*;
import chess.observers.TimerObserver;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static chess.model.ChessColor.BLACK;
import static chess.model.ChessColor.WHITE;

public class Game implements TimerObserver, IGameContext {
    private final List<GameObserver> gameObservers = new ArrayList<>();
    private final List<EndGameObserver> endGameObservers = new ArrayList<>();
    private final Board board = new Board();
    //private final Map<Point, Piece> boardMap = board.getBoardMap(); //Representation of the relationship between points (squares) and pieces on the board

    private final List<Point> legalPoints = new ArrayList<>(); //List of points that are legal to move to for the currently marked point
    private final List<Ply> plies = new ArrayList<>(); //A ply is the technical term for a player's move, and this is a list of moves

    private final Player playerWhite = new Player("Player 1", WHITE);
    private final Player playerBlack = new Player("Player 2", BLACK);
    private Player currentPlayer;

    GameState gameState;

    @Override
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    private void initGameStates(){
        gameState = new NoPieceSelectedState(false,this);
    }

    public void initGame() {
        board.initBoard();
        currentPlayer = playerWhite;
        initGameStates();
    }

    public void initTimers() {
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
        if(gameState.getIsPlayerSwitch()){
            switchPlayer();
        }
        checkGameOver();
        System.out.println(gameState.getGameStatus());
    }

    private void checkGameOver(){
        if(gameState.getIsGameOver()){
            notifyEndGame();
        }
    }

    private void switchPlayer() {
        currentPlayer.getTimer().setActive(false);
        if (currentPlayer == playerWhite) {
            currentPlayer = playerBlack;
        } else if (currentPlayer == playerBlack) {
            currentPlayer = playerWhite;
        }
        currentPlayer.getTimer().setActive(true);
        notifySwitchedPlayer();
        System.out.println("switched player to " + currentPlayer.getName());
    }

    void stopAllTimers(){
        playerBlack.getTimer().stopTimer();
        playerWhite.getTimer().stopTimer();
    }

    @Override
     public void updateTimer() {
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.updateTimer();
        }
    }

    public void gameIsADraw(){
        setGameState(new GameOverState("Game ended in draw", this));
        stopAllTimers();
        notifyEndGame();
    }

    public void gameForfeit(){
        switchPlayer();
        setGameState(new GameOverState(currentPlayer.getName() + " has won the game",this));
        stopAllTimers();
        notifyEndGame();
    }


    public void notifyEndGame() {
        gameObservers.forEach(p -> {
            p.checkEndGame(gameState.getGameStatus());
        });
    }

    public void notifyTimerEnded() {
        switchPlayer();
        setGameState(new GameOverState(currentPlayer.getName() + " has won the game",this));
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

    public void addEndGameObserver(EndGameObserver endGameObserver){
        endGameObservers.add(endGameObserver);
    }

    public void addGameObserver(GameObserver gameObserver) {
        gameObservers.add(gameObserver);
    }

    public void removeGameObserver(GameObserver gameObserver) {
        gameObservers.remove(gameObserver);
    }

    public List<Point> getLegalPoints() {
        return legalPoints;
    }

    public Board getBoard() {
        return board;
    }

    public Player getPlayerWhite() {
        return playerWhite;
    }

    public Player getPlayerBlack() {
        return playerBlack;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public List<Ply> getPlies() {
        return plies;
    }
}
