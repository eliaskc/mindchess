package mindchess.model;

import mindchess.model.*;
import mindchess.model.pieces.IPiece;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestAlternatingTurns {
    ChessFacade model;
    IBoard board;

    @Before
    public void init() {
        model = new ChessFacade();
        model.createNewGame("White", "Black", PlayerType.HUMAN, PlayerType.HUMAN, 180);
        board = model.getCurrentBoard();
    }

    @Test
    public void testAlternatingTurns(){
        String p1 = model.getCurrentWhitePlayerName();
        String p2 = model.getCurrentBlackPlayerName();
        model.handleBoardInput(0,6);
        model.handleBoardInput(0, 5);

        String currPlayer = model.getCurrentPlayerName();
        assertTrue(currPlayer.equals(p2) && !currPlayer.equals(p1));
    }

    @Test
    public void testTakePieceSuccess(){
        IPiece IPiece = model.getCurrentBoardMap().get(new Square(3,7));

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
        IPiece IPiece = model.getCurrentBoardMap().get(new Square(4, 0));

        model.handleBoardInput(3,0);
        model.handleBoardInput(4,0);

        assertFalse(model.getCurrentDeadPieces().contains(IPiece));
    }

}
