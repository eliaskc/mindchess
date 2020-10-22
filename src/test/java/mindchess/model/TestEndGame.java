package mindchess.model;

import mindchess.model.enums.PlayerType;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

public class TestEndGame {
    ChessFacade model;

    @Before
    public void init() {
        model = new ChessFacade();
    }

    //Test taking the king
    //gamelist = 0 after taking a king
    @Test
    public void testCheckKingTaken(){
        model.createNewGame("White", "Black", PlayerType.HUMAN, PlayerType.HUMAN, 180);
        //Setup
        model.handleBoardInput(2,6);
        model.handleBoardInput(2,4);

        model.handleBoardInput(3,1);
        model.handleBoardInput(3,3);

        model.handleBoardInput(3,7);
        model.handleBoardInput(0,4);

        model.handleBoardInput(3,3);
        model.handleBoardInput(2,4);

        //white queen takes takes black king
        model.handleBoardInput(0,4);
        model.handleBoardInput(4,0);

        assertFalse(model.isGameOngoing());
    }

    @Test
    public void testTimerRunningOut() throws InterruptedException {
        model.createNewGame("White", "Black", PlayerType.HUMAN, PlayerType.HUMAN, 0);
        model.initTimersInCurrentGame();
        TimeUnit.SECONDS.sleep(1);
        assertFalse(model.isGameOngoing());
    }

    @Test
    public void testTimerNotRunningOut() {
        model.createNewGame("White", "Black", PlayerType.HUMAN, PlayerType.HUMAN, 5);
        model.initTimersInCurrentGame();
        assertTrue(model.isGameOngoing());
    }

    @Test
    public void testDrawAccepted() {
        model.createNewGame("White", "Black", PlayerType.HUMAN, PlayerType.HUMAN, 180);
        model.acceptDraw();

        assertFalse(model.isGameOngoing());
    }

    @Test
    public void testForfeit() {
        model.createNewGame("White", "Black", PlayerType.HUMAN, PlayerType.HUMAN, 180);
        model.forfeit();

        assertFalse(model.isGameOngoing());
    }
}
