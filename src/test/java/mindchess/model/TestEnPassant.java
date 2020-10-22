package mindchess.model;

import mindchess.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TestEnPassant {
    ChessFacade model;
    Board board;

    @Before
    public void init() {
        model = new ChessFacade();
        model.createNewGame("White", "Black", PlayerType.HUMAN, PlayerType.HUMAN, 180);
        board = model.getCurrentBoard();
    }

    @Test
    public void testEnPassantPossible() {
        //En Passant setup
        model.handleBoardInput(4,6);
        model.handleBoardInput(4,4);

        model.handleBoardInput(0,1);
        model.handleBoardInput(0,2);

        model.handleBoardInput(4,4);
        model.handleBoardInput(4,3);

        model.handleBoardInput(3,1);
        model.handleBoardInput(3,3);

        //The actual en passant
        model.handleBoardInput(4,3);
        model.handleBoardInput(3,2);

//      assertEquals("Pawn", board.fetchPieceOnPoint(new Point(3,2)).getPieceName());
        assertTrue(board.pieceOnSquareColorEquals(new Square(3,2), ChessColor.WHITE));
        assertEquals(null, board.fetchPieceOnSquare(new Square(3,3)));
    }
}
