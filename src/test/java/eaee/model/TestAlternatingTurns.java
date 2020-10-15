package eaee.model;

import chess.model.ChessFacade;
import chess.model.Movement;
import chess.model.Piece;
import chess.model.Player;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TestAlternatingTurns {
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

    @Test
    public void testAlternatingTurns(){
        Player p1 = model.getCurrentGame().getPlayerWhite();
        Player p2 = model.getCurrentGame().getPlayerBlack();
        model.handleBoardInput(0,6);
        model.handleBoardInput(0, 5);

        Player currPlayer = model.getCurrentGame().getCurrentPlayer();
        assertTrue(currPlayer.equals(p2) && !currPlayer.equals(p1));
    }

    @Test
    public void testTakePieceSuccess(){
        Piece piece = model.getCurrentGame().getBoard().getBoardMap().get(new Point(3,7));

        model.handleBoardInput(4,6);
        model.handleBoardInput(4,5);

        model.handleBoardInput(3,1);
        model.handleBoardInput(3,2);

        model.handleBoardInput(3,7);
        model.handleBoardInput(6,4);

        model.handleBoardInput(2,0);
        model.handleBoardInput(6,4);

        assertTrue(model.getGame().getBoard().getDeadPieces().contains(piece));
    }

    @Test
    public void testTakePieceFail(){
        Piece piece = model.getCurrentGame().getBoard().getBoardMap().get(new Point(4, 0));

        model.handleBoardInput(3,0);
        model.handleBoardInput(4,0);

        assertFalse(model.getGame().getBoard().getDeadPieces().contains(piece));
    }
}
