package eaee.model;

import chess.model.*;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class TestChessTimer {
    ChessFacade model;
    Map<Square, Piece> boardMap = new HashMap<>();
    Movement movement = new Movement();

    @Before
    public void init() {
        model = new ChessFacade();
        model.createNewGame();
        boardMap = model.getCurrentBoardMap();
        movement.setBoardMap(boardMap);
    }

    @Test
    public void testTimer() throws InterruptedException {
        model.setCurrentWhitePlayerTimerTime(10);
        model.initTimersInCurrentGame();
        TimeUnit.SECONDS.sleep(1);
        assertTrue(model.getCurrentWhiteTimerTime() < 10);
        model.stopAllTimers();
    }
}