package eaee.model;

import chess.model.Board;
import chess.model.ChessFacade;
import chess.model.pieces.IPiece;
import chess.model.pieces.PieceMovementLogic;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class TestCastling {
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
    public void testCastlingNotPossibleWrongPosition() {
        model.handleBoardInput(4,7);
        model.handleBoardInput(6,7);

        assertEquals(board.fetchPieceOnPoint(new Point(5, 7)).getPieceName(), "Bishop");
        assertEquals(board.fetchPieceOnPoint(new Point(6, 7)).getPieceName(), "Knight");
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

        assertEquals("Rook", board.fetchPieceOnPoint(new Point(5, 7)).getPieceName());
        assertEquals("King", board.fetchPieceOnPoint(new Point(6, 7)).getPieceName());
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

        assertNotEquals("King", board.fetchPieceOnPoint(new Point(6, 7)));
    }
}
