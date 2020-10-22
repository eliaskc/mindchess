package mindchess.model;

import mindchess.model.enums.ChessColor;
import mindchess.model.enums.PlayerType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests the special move en passant
 */
public class TestEnPassant {
    ChessFacade model;
    Board board;

    @Before
    public void init() {
        model = new ChessFacade();
        model.createNewGame("White", "Black", PlayerType.HUMAN, PlayerType.HUMAN, 180);
        board = model.getCurrentBoard();
    }

    /**
     * Tests if you can make a en passant when it is allowed
     */
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

    /**
     * Tests that you can not make an en passant when the pawn was not the last moved piece
     */
    @Test
    public void testEnPassantNotPossiblePawnNotLastMoved() {
        //En Passant setup
        model.handleBoardInput(4,6);
        model.handleBoardInput(4,4);

        model.handleBoardInput(3,1);
        model.handleBoardInput(3,3);

        model.handleBoardInput(4,4);
        model.handleBoardInput(4,3);

        model.handleBoardInput(3,0);
        model.handleBoardInput(3,1);

        //The actual en passant
        model.handleBoardInput(4,3);
        model.handleBoardInput(3,2);

        assertEquals(null, board.fetchPieceOnSquare(new Square(3,2)));
    }

    /**
     * Tests that you can not make an en passant when the pawn made a one step move last move
     */
    @Test
    public void testEnPassantNotPossiblePawnOnlyMoveOneStep() {
        //En Passant setup
        model.handleBoardInput(4,6);
        model.handleBoardInput(4,4);

        model.handleBoardInput(3,1);
        model.handleBoardInput(3,2);

        model.handleBoardInput(4,4);
        model.handleBoardInput(4,3);

        model.handleBoardInput(3,2);
        model.handleBoardInput(3,3);

        //The actual en passant
        model.handleBoardInput(4,3);
        model.handleBoardInput(3,2);

        assertEquals(null, board.fetchPieceOnSquare(new Square(3,2)));
    }
}
