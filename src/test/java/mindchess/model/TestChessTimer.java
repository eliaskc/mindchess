package mindchess.model;

import mindchess.model.enums.PlayerType;
import org.junit.Before;

public class TestChessTimer {
    ChessFacade model;

    @Before
    public void init() {
        model = new ChessFacade();
        model.createNewGame("White", "Black", PlayerType.HUMAN, PlayerType.HUMAN, 180);
    }

    /*@Test
    public void testTimer() throws InterruptedException {
        model.setCurrentWhitePlayerTimerTime(10);
        model.initTimersInCurrentGame();
        TimeUnit.SECONDS.sleep(1);
        assertTrue(model.getCurrentWhiteTimerTime() < 10);
        model.stopAllTimers();
    }*/
}