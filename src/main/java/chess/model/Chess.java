package chess.model;

import chess.Observable;
import chess.Observer;
import chess.model.pieces.Piece;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import javafx.fxml.FXML;

import java.net.SocketTimeoutException;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Chess implements Observable {
    private static Chess instance = null;

    private List<Observer> observers = new ArrayList<>();

    Player player1 = new Player("Player 1");
    Player player2 = new Player("Player 2");

    Board board = new Board();

    List<Move> moves = new ArrayList<>();

    public static Chess getInstance() {
        if (instance == null) {
            instance = new Chess();
            instance.init();
        }
        return instance;
    }

    private void init() {
        board.initializeBoard();
        board.pieces.add(PieceFactory.createQueen(board.getSquares()[0][0],true, Color.WHITE));
        board.pieces.add(PieceFactory.createQueen(board.getSquares()[7][5],true, Color.BLACK));
        board.fetchPieceImages();
    }

    public void startGame() {}

    public void endGame() {}

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Board getBoard() {
        return board;
    }

    /**
     *
     * @param mouseX
     * @param mouseY
     */
    public void handleClick(double mouseX, double mouseY){
        board.handleClick(mouseX, mouseY);

        notifyAllObservers();
    }

    @Override
    public void notifyAllObservers() {
        for(Observer observer : observers) {
            observer.onAction();
        }
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }
}
