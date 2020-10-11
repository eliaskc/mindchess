package eaee.model;

import chess.model.ChessFacade;
import chess.model.Movement;
import chess.model.Piece;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TestEndGame {
    ChessFacade model;
    Map<Point, Piece> boardMap = new HashMap<>();
    Movement movement = new Movement();

    @Before
    public void init() {
        model = new ChessFacade();
        model.createNewGame();
        boardMap = model.getCurrentGame().getBoard().getBoardMap();
        movement.setBoardMap(boardMap);
    }

    //Test taking the king
    //gamelist = 0 after taking a king
    @Test
    public void testCheckKingTaken(){
        //Setup
        model.handleBoardClick(2,6);
        model.handleBoardClick(2,4);

        model.handleBoardClick(3,1);
        model.handleBoardClick(3,3);

        model.handleBoardClick(3,7);
        model.handleBoardClick(0,4);

        model.handleBoardClick(3,3);
        model.handleBoardClick(2,4);

        //white queen takes takes black king
        model.handleBoardClick(0,4);
        model.handleBoardClick(4,0);
        assertEquals(true, model.getCurrentGame().isGameHasEnded());
    }

    @Test
    public void testTimerRunningOut() {
        model.getCurrentGame().getPlayerWhite().getTimer().setTime(0);

        model.getCurrentGame().checkTimerRanOut();

        assertEquals(true, model.getCurrentGame().isGameHasEnded());
    }

    @Test
    public void testDrawAccepted() {
        model.getCurrentGame().offerDraw();
        model.getCurrentGame().acceptDraw();

        assertEquals(true, model.getCurrentGame().isGameHasEnded());
    }

    @Test
    public void testDrawDeclined() {
        model.getCurrentGame().offerDraw();
        model.getCurrentGame().declineDraw();

        assertEquals(true, model.getCurrentGame().isGameHasEnded());
    }

    @Test
    public void testForfeit() {
        model.getCurrentGame().forfeit();

        assertEquals(true, model.getCurrentGame().isGameHasEnded());
    }
}
