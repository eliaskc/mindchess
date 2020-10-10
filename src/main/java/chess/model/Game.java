package chess.model;

import chess.GameObserver;
import chess.model.GameState.GameState;
import chess.model.GameState.NoPieceSelectedState;
import chess.model.GameState.PieceSelectedState;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static chess.model.ChessColor.BLACK;
import static chess.model.ChessColor.WHITE;
import static chess.model.PieceType.PAWN;

public class Game implements TimerObserver, IGameContext {
    private final List<GameObserver> gameObservers = new ArrayList<>();
    private final Board board = new Board();
    private final Map<Point, Piece> boardMap = board.getBoardMap(); //Representation of the relationship between points (squares) and pieces on the board

    private final List<Piece> deadPieces = new ArrayList<>();
    private final List<Point> legalPoints = new ArrayList<>(); //List of points that are legal to move to for the currently marked point
    private final List<Ply> plies = new ArrayList<>(); //A ply is the technical term for a player's move, and this is a list of moves

    private final Player playerWhite = new Player("Player 1", WHITE);
    private final Player playerBlack = new Player("Player 2", BLACK);
    private Player currentPlayer;

    private Point markedPoint = null; //Used to keep track of the currently marked point/piece so that it can be moved

    private boolean pawnPromotionInProgress = false;
    private Point pawnPromotionPoint; //The point at which a pawn is being promoted

    private final Movement movement = new Movement(boardMap,plies);

    GameState gameState;
    GameState noPiecesSelected;
    GameState pieceSelected;

    @Override
    public void setGameState(GameStates gameState) {
        switch (gameState){
            case NoPieceSelected -> this.gameState = noPiecesSelected;
            case PieceSelected -> this.gameState =  pieceSelected;
        }
    }

    @Override
    public GameState getGameState() {
        return gameState;
    }

    private void initGameStates(){
        noPiecesSelected = new NoPieceSelectedState(this);
        pieceSelected = new PieceSelectedState(this);
        gameState = noPiecesSelected;
    }

    public void initGame() {
        board.initBoard();
        playerWhite.setPieces(board.getPiecesByColor(WHITE));
        playerBlack.setPieces(board.getPiecesByColor(BLACK));
        playerWhite.setOpponent(playerBlack);
        playerBlack.setOpponent(playerWhite);
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
    void handleBoardClick(int x, int y) {
        gameState.handleInput(x,y);
    }

    private void winConditionCheck(){
        checkKingTaken();
    }

    private void checkKingTaken(){
        if (!(deadPieces.size() == 0)) {
            Piece lastPieceTaken = deadPieces.get(deadPieces.size()-1);
            if (lastPieceTaken.getPieceType() == PieceType.KING) {
                if (lastPieceTaken.getColor() == BLACK) whitePlayerWin();
                else if (lastPieceTaken.getColor() == WHITE) blackPlayerWin();
            }
        }
    }

    private void whitePlayerWin(){
        notifyEndGameObservers("white");
    }
    private void blackPlayerWin(){
        notifyEndGameObservers("black");
    }

    void notifyEndGameObservers(String result) {
        gameObservers.forEach(p -> {
            p.checkEndGame(result);
        });
    }
    /**
     * Checks if pawn a pawn is in a position to be promoted and initiates the promotion if so
     *
     * @param clickedPoint
     */
    private boolean checkPawnPromotion(Point clickedPoint) {
        if (boardMap.get(clickedPoint).getPieceType() == PAWN) {
            if ((clickedPoint.y == 0 && boardMap.get(clickedPoint).getColor() == WHITE) || (clickedPoint.y == 7 && boardMap.get(clickedPoint).getColor() == BLACK)) {
                notifyPawnPromotion(boardMap.get(clickedPoint).getColor());
                pawnPromotionInProgress = true;
                pawnPromotionPoint = new Point(clickedPoint.x, clickedPoint.y);
            }
        }
        return pawnPromotionInProgress;
    }

    /**
     * Promotes the pawn being promoted to the selected type
     *
     * @param pieceType
     */
    public void pawnPromotion(PieceType pieceType) {
        boardMap.get(pawnPromotionPoint).setPieceType(pieceType);
        pawnPromotionInProgress = false;
        pawnPromotionPoint = null;

        switchPlayer();
        notifyDrawPieces();

    }

    private void switchPlayer() {
        currentPlayer.getTimer().setActive(false);
        if (currentPlayer == playerWhite) {
            currentPlayer = playerBlack;
        } else if (currentPlayer == playerBlack) {
            currentPlayer = playerWhite;
        }
        currentPlayer.getTimer().setActive(true);
    }

    void stopAllTimers(){
        playerBlack.getTimer().setActive(false);
        playerWhite.getTimer().setActive(false);
    }

    @Override
     public void updateTimer() {
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.updateTimer();
        }
    }

    public void timerGameEnd() {
        if(playerWhite.getTimer().getTime()==0){
            notifyEndGameObservers("black");
        } else if (playerBlack.getTimer().getTime()==0) {
            notifyEndGameObservers("white");
        }
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
        switchPlayer();
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.switchedPlayer();
        }
    }

    private void notifyPawnPromotion(ChessColor chessColor) {
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.pawnPromotionSetup(chessColor);
        }
    }

    public void addObserver(GameObserver gameObserver) {
        gameObservers.add(gameObserver);
    }

    public void removeObserver(GameObserver gameObserver) {
        gameObservers.remove(gameObserver);
    }

    public List<Point> getLegalPoints() {
        return legalPoints;
    }

    public Board getBoard() {
        return board;
    }

    public Map<Point, Piece> getBoardMap() {
        return boardMap;
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

    public List<Piece> getDeadPieces() {
        return deadPieces;
    }

    public List<Ply> getPlies() {
        return plies;
    }

    public Movement getMovement() {
        return movement;
    }
}
