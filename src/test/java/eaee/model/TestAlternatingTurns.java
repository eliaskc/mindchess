package eaee.model;

import chess.model.Board;
import chess.model.ChessFacade;
import chess.model.pieces.IPiece;
import chess.model.pieces.PieceMovementLogic;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class TestAlternatingTurns {
    ChessFacade model;
    Board board;
    PieceMovementLogic pieceMovementLogic = PieceMovementLogic.getInstance();

    @Before
    public void init() {
        model = new ChessFacade();
        model.createNewGame();
        board = model.getCurrentBoard();
        pieceMovementLogic.setBoard(board);
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
        IPiece IPiece = model.getCurrentBoardMap().get(new Point(3,7));

        model.handleBoardInput(4,6);
        model.handleBoardInput(4,5);

        model.handleBoardInput(3,1);
        model.handleBoardInput(3,2);

        model.handleBoardInput(3,7);
        model.handleBoardInput(6,4);

        model.handleBoardInput(2,0);
        model.handleBoardInput(6,4);

        assertTrue(model.getCurrentDeadPieces().contains(IPiece));
    }

    @Test
    public void testTakePieceFail(){
        IPiece IPiece = model.getCurrentBoardMap().get(new Point(4, 0));

        model.handleBoardInput(3,0);
        model.handleBoardInput(4,0);

        assertFalse(model.getCurrentDeadPieces().contains(IPiece));
    }

}
