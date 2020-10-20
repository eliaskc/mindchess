package eaee.model;

import chess.model.*;
import org.junit.Before;
import static org.junit.Assert.*;
import org.junit.Test;

import java.util.concurrent.TimeUnit;

public class TestChessTimer {
    ChessFacade model;

    @Before
    public void init() {
        model = new ChessFacade();
        model.createNewGame();
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