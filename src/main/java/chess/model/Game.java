package chess.model;

import chess.observers.EndGameObserver;
import chess.observers.GameObserver;
import chess.observers.TimerObserver;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static chess.model.ChessColor.BLACK;
import static chess.model.ChessColor.WHITE;
import static chess.model.PieceType.PAWN;

public class Game implements TimerObserver {
    private final List<GameObserver> gameObservers = new ArrayList<>();
    private final List<EndGameObserver> endGameObservers = new ArrayList<>();

    private final Board board = new Board();
    private final Movement movement = new Movement();
    private final Map<Point, Piece> boardMap = board.getBoardMap(); //Representation of the relationship between points (squares) and pieces on the board

    private final List<Piece> deadPieces = new ArrayList<>();
    private final List<Point> legalPoints = new ArrayList<>(); //List of points that are legal to move to for the currently marked point
    private final List<Ply> plies = new ArrayList<>(); //A ply is the technical term for a player's move, and this is a list of moves

    private final Player playerWhite = new Player("Player 1", WHITE);
    private final Player playerBlack = new Player("Player 2", BLACK);
    private Player currentPlayer;

    private Point markedPoint = null; //Used to keep track of the currently marked point/piece so that it can be moved

    private boolean allowedToMovePieces = true;
    private Point pawnPromotionPoint; //The point at which a pawn is being promoted

    private boolean gameHasEnded = false;

    public void initGame() {
        board.initBoard();
        movement.setBoardMap(boardMap);
        movement.setPlies(plies);
        playerWhite.setPieces(board.getPiecesByColor(WHITE));
        playerBlack.setPieces(board.getPiecesByColor(BLACK));
        currentPlayer = playerWhite;
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
        if (!allowedToMovePieces) {
            return;
        }

        //The last point/square that has been clicked on
        Point clickedPoint = new Point(x, y);

        //Only marks a clicked piece if it is your own
        if (clickedOwnPiece(clickedPoint)) {
            if (checkDeselection(clickedPoint)) return;
            markedPoint = new Point(x, y);
        }

        if (legalPoints.size() == 0 && boardMap.get(markedPoint) != null) {
            fetchLegalMoves();
        } else {
            checkMove(clickedPoint);
        }

        notifyDrawLegalMoves();
    }

    private boolean clickedOwnPiece(Point p) {
        if (boardMap.containsKey(p)) {
            return (boardMap.get(p).getColor() == currentPlayer.getColor());
        }
        return false;
    }

    private boolean checkDeselection(Point clickedPoint) {
        if (markedPoint != null) {
            legalPoints.clear();
            if (markedPoint.equals(clickedPoint)) {
                markedPoint = null;
                notifyDrawLegalMoves();
                return true;
            }
        }
        return false;
    }

    /**
     * Adds all legal points the marked piece can move to to the legalPoints list
     */
    private void fetchLegalMoves() {
        legalPoints.addAll(movement.pieceMoveDelegation(boardMap.get(markedPoint), markedPoint));

        //This is needed otherwise an empty list wouldn't nullify markedPoint
        if (legalPoints.size() == 0) {
            markedPoint = null;
        }
    }

    /**
     * Checks if the latest click was on a point that is legal to move to
     * If it is, the move is made
     *
     * @param clickedPoint
     */
    private void checkMove(Point clickedPoint) {
        if (legalPoints.contains(clickedPoint)) {
            Ply ply = new Ply(markedPoint, clickedPoint, boardMap.get(markedPoint), currentPlayer);
            plies.add(ply);
            ply.generateBoardSnapshot(boardMap);
            makeSpecialMoves(markedPoint, clickedPoint);
            move(markedPoint, clickedPoint);
            if (checkPawnPromotion(clickedPoint)) {
                switchPlayer();
            }
            winConditionCheck();
            notifyDrawPieces();
        }
        legalPoints.clear();
        markedPoint = null;
    }

    /**
     * Checks if any special moves are attempted and if so, performs the necessary actions
     *
     * @param markedPoint
     * @param clickedPoint
     */
    private void makeSpecialMoves(Point markedPoint, Point clickedPoint) {
        //castling
        if (movement.getCastlingPoints().size() != 0 && movement.getCastlingPoints().contains(clickedPoint)) {
            if (clickedPoint.x > markedPoint.x) {
                move(new Point(clickedPoint.x + 1, clickedPoint.y), new Point(clickedPoint.x - 1, clickedPoint.y));
            } else if (clickedPoint.x < markedPoint.x) {
                move(new Point(clickedPoint.x - 2, clickedPoint.y), new Point(clickedPoint.x + 1, clickedPoint.y));
            }
        }

        //en passant
        if (movement.getEnPassantPoints().size() != 0 && movement.getEnPassantPoints().contains(clickedPoint)) {
            if (boardMap.get(markedPoint).getColor() == WHITE) {
                takePiece(new Point(clickedPoint.x, clickedPoint.y + 1));
            } else if (boardMap.get(markedPoint).getColor() == BLACK) {
                takePiece(new Point(clickedPoint.x, clickedPoint.y - 1));
            }
        }
    }

    /**
     * Moves the marked piece to the clicked point
     * <p>
     */
    private void move(Point moveFrom, Point moveTo) {
        if (boardMap.get(moveTo) != null) {
            takePiece(moveTo);
        }

        boardMap.put(moveTo, boardMap.get(moveFrom));
        boardMap.remove(moveFrom);
    }

    /**
     * Removes a piece on a point from the boardmap and adds it to the deadpieces list and draws it in the interface
     *
     * @param pointToTake the point the piece is removed from
     */
    private void takePiece(Point pointToTake) {
        deadPieces.add(boardMap.remove(pointToTake));
        notifyDrawDeadPieces();
    }

    /**
     * Checks the different winconditions that can happen during a game
     */
    private void winConditionCheck() {
        checkKingTaken();
    }

    /**
     * checks if a king is taken and if so determines who won by taking the opponents king
     */
    private void checkKingTaken() {
        if (!(deadPieces.size() == 0)) {
            Piece lastPieceTaken = deadPieces.get(deadPieces.size() - 1);
            if (lastPieceTaken.getPieceType() == PieceType.KING) {
                if (lastPieceTaken.getColor() == BLACK) whitePlayerWin();
                else if (lastPieceTaken.getColor() == WHITE) blackPlayerWin();
            }
        }
    }

    //Should this go through chessFacade

    public void offerDraw() {
        allowedToMovePieces = false;
    }

    public void declineDraw() {
        allowedToMovePieces = true;
    }

    public void acceptDraw() {
        notifyEndGameObservers("draw");
    }

    /**
     * handles forfeits, makes the current player lose the game
     */
    public void forfeit() {
        if (currentPlayer.getColor().equals(WHITE)) blackPlayerWin();
        else whitePlayerWin();
    }

    /**
     * sets the white player as winner
     */
    private void whitePlayerWin() {
        notifyEndGameObservers("white");
    }

    /**
     * sets the black player as winner
     */
    private void blackPlayerWin() {
        notifyEndGameObservers("black");
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
                setAllowedToMovePieces(false);
                pawnPromotionPoint = new Point(clickedPoint.x, clickedPoint.y);
            }
        }
        return allowedToMovePieces;
    }

    /**
     * Promotes the pawn being promoted to the selected type
     *
     * @param pieceType
     */
    public void pawnPromotion(PieceType pieceType) throws NullPointerException {
        try {
            boardMap.get(pawnPromotionPoint).setPieceType(pieceType);
        } catch (NullPointerException e) {
            System.out.println("No pawn to be promoted exists.");
        }
        setAllowedToMovePieces(true);
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
        notifySwitchedPlayer();
    }

    void stopAllTimers() {
        playerBlack.getTimer().setActive(false);
        playerWhite.getTimer().setActive(false);
    }

    @Override
    public void updateTimer() {
        notifyTimerUpdated();
    }

    public void checkTimerRanOut() {
        if (playerWhite.getTimer().getTime() == 0) {
            notifyEndGameObservers("black");
        } else if (playerBlack.getTimer().getTime() == 0) {
            notifyEndGameObservers("white");
        }
    }

    private void notifyTimerUpdated() {
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.updateTimer();
        }
    }

    private void notifyDrawPieces() {
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.drawPieces();
        }
    }

    private void notifyDrawDeadPieces() {
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.drawDeadPieces();
        }
    }

    private void notifyDrawLegalMoves() {
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.drawLegalMoves();
        }
    }

    private void notifySwitchedPlayer() {
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.switchedPlayer();
        }
    }

    private void notifyPawnPromotion(ChessColor chessColor) {
        for (GameObserver gameObserver : gameObservers) {
            gameObserver.pawnPromotionSetup(chessColor);
        }
    }

    void notifyEndGameObservers(String result) {
        endGameObservers.forEach(p -> {
            p.endGame(result);
        });
    }

    public void addGameObserver(GameObserver gameObserver) {
        gameObservers.add(gameObserver);
    }

    public void removeGameObserver(GameObserver gameObserver) {
        gameObservers.remove(gameObserver);
    }

    public void addEndGameObserver(EndGameObserver endgameObserver) {
        endGameObservers.add(endgameObserver);
    }

    public void removeEndGameObserver(EndGameObserver endgameObserver) {
        endGameObservers.remove(endgameObserver);
    }

    public List<Point> getLegalPoints() {
        return legalPoints;
    }

    public Point getMarkedPoint() {
        return markedPoint;
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

    public List<Piece> getDeadPieces() {
        return deadPieces;
    }

    public List<Ply> getPlies() {
        return plies;
    }

    public boolean isGameHasEnded() {
        return gameHasEnded;
    }

    public void setGameHasEnded(boolean gameHasEnded) {
        this.gameHasEnded = gameHasEnded;
    }

    public void setAllowedToMovePieces(boolean allowedToMovePieces) {
        this.allowedToMovePieces = allowedToMovePieces;
    }
}
