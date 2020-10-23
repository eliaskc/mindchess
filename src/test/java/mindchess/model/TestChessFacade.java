package mindchess.model;

import mindchess.model.enums.ChessColor;
import mindchess.model.enums.PlayerType;
import mindchess.model.pieces.IPiece;
import org.junit.Before;
import org.junit.Test;


import static mindchess.model.enums.PieceType.*;
import static org.junit.Assert.*;

/**
 * Tests for the class chessFacade
 *
 * @author Erik Wessman, Elias Carlson, Elias Hallberg, Arvid Holmqvist
 */
public class TestChessFacade {
    private ChessFacade model;
    IBoard board;

    @Before
    public void init() {
        model = new ChessFacade();
        model.createNewGame("White", "Black", PlayerType.HUMAN, PlayerType.HUMAN, 180);
        board = model.getCurrentBoard();
    }

    /**
     * Tests that you can move pieces
     *
     * Tests that the moves you make are saved with all relevant information
     */
    @Test
    public void testMoveAndPly() {
        Square p1 = new Square(0,6);
        Square p2 = new Square(0,5);

        IPiece testIPieceBefore = model.getCurrentBoardMap().get(p1);

        model.handleBoardInput(0,6);
        model.handleBoardInput(0,5);

        IPiece testIPieceAfter = model.getCurrentBoardMap().get(p2);

        Ply ply = new Ply("", p1,p2, testIPieceAfter,null,model.getCurrentBoardMap());

        assertEquals(testIPieceBefore, testIPieceAfter);
        assertEquals(model.getCurrentGamePlies().get(0).getMovedFrom(), ply.getMovedFrom());
        assertEquals(model.getCurrentGamePlies().get(0).getMovedTo(), ply.getMovedTo());
        assertEquals(model.getCurrentGamePlies().get(0).getMovedPiece(), ply.getMovedPiece());
    }

    /**
     * Tests that pieces are put on the corect places
     */
    @Test
    public void testPlaceAllPieces(){
        assertTrue(board.pieceOnSquareColorEquals(new Square(0,0), ChessColor.BLACK) && board.fetchPieceOnSquare(new Square(0,0)).getPieceType().equals(ROOK));
        assertTrue(board.pieceOnSquareColorEquals(new Square(6,6), ChessColor.WHITE) && board.fetchPieceOnSquare(new Square(6,6)).getPieceType().equals(PAWN));
        assertTrue(board.pieceOnSquareColorEquals(new Square(4,7), ChessColor.WHITE) && board.fetchPieceOnSquare(new Square(4,7)).getPieceType().equals(KING));
    }

    /**
     * Tests that the program does not crash when you click outside the board
     */
    @Test
    public void testClickOutsideBoard() {
        model.handleBoardInput(10,11);
        model.handleBoardInput(10,12);
    }
}