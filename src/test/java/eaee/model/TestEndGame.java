package eaee.model;

import chess.model.ChessFacade;
import chess.model.pieces.PieceMovementLogic;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.util.concurrent.TimeUnit;

public class TestEndGame {
    ChessFacade model;
    PieceMovementLogic pieceMovementLogic = new PieceMovementLogic();

    @Before
    public void init() {
        model = new ChessFacade();
        model.createNewGame();
        pieceMovementLogic.setBoard(model.getCurrentBoard());
    }

    //Test taking the king
    //gamelist = 0 after taking a king
    @Test
    public void testCheckKingTaken(){
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
        model.setCurrentWhitePlayerTimerTime(0);
        model.initTimersInCurrentGame();
        TimeUnit.SECONDS.sleep(1);
        assertFalse(model.isGameOngoing());
    }

    @Test
    public void testTimerNotRunningOut() {
        model.setCurrentWhitePlayerTimerTime(5);
        model.initTimersInCurrentGame();
        assertTrue(model.isGameOngoing());
    }


    //Currently the accepting/declining is not done by the model
    /*
    @Test
    public void testDrawAccepted() {
        model.getCurrentGame().offerDraw();
        model.getCurrentGame().acceptDraw();

        assertEquals(true, model.getCurrentGame().getGameState().getIsGameOver());
    }

    @Test
    public void testDrawDeclined() {
        model.getCurrentGame().offerDraw();
        model.getCurrentGame().declineDraw();

        assertEquals(false, model.getCurrentGame().getGameState().getIsGameOver());
    }*/

    @Test
    public void testForfeit() {
        model.forfeit();

        assertFalse(model.isGameOngoing());
    }
}
