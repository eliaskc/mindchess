package mindchess.model;

import mindchess.model.enums.PlayerType;
import mindchess.model.pieces.IPiece;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class TestTakePieces {
    ChessFacade model;
    IBoard board;

    @Before
    public void init() {
        model = new ChessFacade();
        model.createNewGame("White", "Black", PlayerType.HUMAN, PlayerType.HUMAN, 180);
        board = model.getCurrentBoard();
    }

    /**
     * Tests that Pieces that are taken are saved in the deadPieces list
     */
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

    /**
     * Tests that if a piece is not taken, it does not get into the deadPieces list
     */
    @Test
    public void testTakePieceFail(){
        IPiece IPiece = model.getCurrentBoardMap().get(new Square(4, 0));

        model.handleBoardInput(3,0);
        model.handleBoardInput(4,0);

        assertFalse(model.getCurrentDeadPieces().contains(IPiece));
    }
}
