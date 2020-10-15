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
        assertEquals(true, model.getCurrentGame().getGameState().getIsGameOver());
    }

    @Test
    public void testTimerRunningOut() {
        model.getCurrentGame().getPlayerWhite().getTimer().setTime(0);

        model.getCurrentGame().notifyTimerEnded();

        assertEquals(true, model.getCurrentGame().getGameState().getIsGameOver());
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
        model.getCurrentGame().endGameAsForfeit();

        assertEquals(true, model.getCurrentGame().getGameState().getIsGameOver());
    }
}
