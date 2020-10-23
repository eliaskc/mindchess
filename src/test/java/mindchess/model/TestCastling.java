package mindchess.model;

import mindchess.model.enums.PieceType;
import mindchess.model.enums.PlayerType;
import org.junit.Before;
import org.junit.Test;

import static mindchess.model.enums.PieceType.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

/**
 * Tests if you can do castleing and if you only can do it when it is allowed
 */
public class TestCastling {
    ChessFacade model;
    IBoard board;

    @Before
    public void init() {
        model = new ChessFacade();
        model.createNewGame("White", "Black", PlayerType.HUMAN, PlayerType.HUMAN, 180);
        board = model.getCurrentBoard();
    }

    /**
     * Tests if you can castle when there are pieces in the path of the castle
     */
    @Test
    public void testCastlingNotPossibleWrongPosition() {
        model.handleBoardInput(4,7);
        model.handleBoardInput(6,7);

        assertEquals(board.fetchPieceOnSquare(new Square(5, 7)).getPieceType(), BISHOP);
        assertEquals(board.fetchPieceOnSquare(new Square(6, 7)).getPieceType(), KNIGHT);
    }

    /**
     * Tests if you can castle when it is allowed
     */
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

    /**
     * Tests if you can not castle when the king has moved before
     */
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

        assertEquals(KING, board.fetchPieceOnSquare(new Square(4, 7)).getPieceType());
    }

    /**
     * Tests if you can not castle when the rook has moved before
     */
    //TODO Implement so that these work
    @Test
    public void testCastlingRookHasMoved() {
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

        model.handleBoardInput(7,7);
        model.handleBoardInput(6,7);

        model.handleBoardInput(0,1);
        model.handleBoardInput(0,2);

        model.handleBoardInput(6,7);
        model.handleBoardInput(7,7);

        model.handleBoardInput(0,2);
        model.handleBoardInput(0,3);

        //The actual castling
        model.handleBoardInput(4,7);
        model.handleBoardInput(6,7);

        assertEquals(KING, board.fetchPieceOnSquare(new Square(4, 7)).getPieceType());
    }

    /**
     * Tests if you can not castle when the king is in check
     */
    @Test
    public void testCastlingKingInCheck() {
        //Castling setup
        model.handleBoardInput(4,7);
        model.handleBoardInput(4,5);

        model.handleBoardInput(4,1);
        model.handleBoardInput(4,3);

        model.handleBoardInput(5,7);
        model.handleBoardInput(0,2);

        model.handleBoardInput(0,5);
        model.handleBoardInput(1,4);

        model.handleBoardInput(6,7);
        model.handleBoardInput(5,7);

        model.handleBoardInput(4,1);
        model.handleBoardInput(3,6);

        //The actual castling attempt
        model.handleBoardInput(4,7);
        model.handleBoardInput(6,7);

        assertEquals(KING, board.fetchPieceOnSquare(new Square(4, 7)).getPieceType());
    }
}
