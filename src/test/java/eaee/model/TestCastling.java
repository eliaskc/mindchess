package eaee.model;

import mindchess.model.*;
import org.junit.Before;
import org.junit.Test;

import static mindchess.model.PieceType.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class TestCastling {
    ChessFacade model;
    Board board;

    @Before
    public void init() {
        model = new ChessFacade();
        model.createNewGame();
        board = model.getCurrentBoard();
    }

    @Test
    public void testCastlingNotPossibleWrongPosition() {
        model.handleBoardInput(4,7);
        model.handleBoardInput(6,7);

        assertEquals(board.fetchPieceOnSquare(new Square(5, 7)).getPieceType(), BISHOP);
        assertEquals(board.fetchPieceOnSquare(new Square(6, 7)).getPieceType(), KNIGHT);
    }

    @Test
    public void testCastlingPossible() {
        //Castling setup
        model.handleBoardInput(6,7);
        model.handleBoardInput(7,5);

        model.handleBoardInput(4,1);
        model.handleBoardInput(4,2);

        model.handleBoardInput(4,6);
        model.handleBoardInput(4,5);

        model.handleBoardInput(4,2);
        model.handleBoardInput(4,3);

        model.handleBoardInput(5,7);
        model.handleBoardInput(4,6);

        model.handleBoardInput(4,3);
        model.handleBoardInput(4,4);

        //The actual castling
        model.handleBoardInput(4,7);
        model.handleBoardInput(6,7);

        assertEquals(ROOK, board.fetchPieceOnSquare(new Square(5, 7)).getPieceType());
        assertEquals(KING, board.fetchPieceOnSquare(new Square(6, 7)).getPieceType());
    }

    @Test
    public void testCastlingKingHasMoved() {
        //Castling setup
        model.handleBoardInput(6,7);
        model.handleBoardInput(7,5);

        model.handleBoardInput(4,1);
        model.handleBoardInput(4,2);

        model.handleBoardInput(4,6);
        model.handleBoardInput(4,5);

        model.handleBoardInput(4,2);
        model.handleBoardInput(4,3);

        model.handleBoardInput(5,7);
        model.handleBoardInput(4,6);

        model.handleBoardInput(4,3);
        model.handleBoardInput(4,4);

        model.handleBoardInput(4,7);
        model.handleBoardInput(5,7);

        model.handleBoardInput(0,1);
        model.handleBoardInput(0,2);

        model.handleBoardInput(5,7);
        model.handleBoardInput(4,7);

        model.handleBoardInput(0,2);
        model.handleBoardInput(0,3);

        //The actual castling
        model.handleBoardInput(4,7);
        model.handleBoardInput(6,7);

        assertNotEquals("King", board.fetchPieceOnSquare(new Square(6, 7)));
    }
}
