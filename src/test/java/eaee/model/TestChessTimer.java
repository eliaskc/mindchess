package eaee.model;

import chess.model.ChessFacade;
import chess.model.ChessTimer;
import chess.model.Movement;
import chess.model.Piece;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TestChessTimer {
    ChessFacade model;
    Map<Point, Piece> boardMap = new HashMap<>();
    Movement movement = new Movement();

    @Before
    public void init() {
        model = ChessFacade.getInstance();
        boardMap = model.getGame().getBoard().getBoardMap();
        movement.setBoardMap(boardMap);
    }

    @Test
    public void testTimer() throws InterruptedException {
        ChessTimer chessTimer = new ChessTimer();
        chessTimer.setTime(10);
        chessTimer.startTimer();
        chessTimer.setActive(true);
        Thread.sleep(1000);
        assertTrue(chessTimer.getTime() < 10);
        chessTimer.stopTimer();
    }
}