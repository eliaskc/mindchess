package eaee.model;

import chess.model.*;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TestAlternatingTurns {
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
    public void testAlternatingTurns(){
        String p1 = model.getCurrentPlayerWhiteName();
        String p2 = model.getCurrentPlayerBlackName();
        model.handleBoardInput(0,6);
        model.handleBoardInput(0, 5);

        String currPlayer = model.getCurrentPlayerName();
        assertTrue(currPlayer.equals(p2) && !currPlayer.equals(p1));
    }

    @Test
    public void testTakePieceSuccess(){
        Piece piece = model.getCurrentBoardMap().get(new Square(3,7));

        model.handleBoardInput(4,6);
        model.handleBoardInput(4,5);

        model.handleBoardInput(3,1);
        model.handleBoardInput(3,2);

        model.handleBoardInput(3,7);
        model.handleBoardInput(6,4);

        model.handleBoardInput(2,0);
        model.handleBoardInput(6,4);

        assertTrue(model.getCurrentDeadPieces().contains(piece));
    }

    @Test
    public void testTakePieceFail(){
        Piece piece = model.getCurrentBoardMap().get(new Square(4, 0));

        model.handleBoardInput(3,0);
        model.handleBoardInput(4,0);

        assertFalse(model.getCurrentDeadPieces().contains(piece));
    }

}
