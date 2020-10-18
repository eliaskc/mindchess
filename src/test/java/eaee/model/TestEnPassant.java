package eaee.model;

import chess.model.Board;
import chess.model.ChessFacade;
import chess.model.ChessColor;
import chess.model.pieces.PieceMovementLogic;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class TestEnPassant {
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

//        assertEquals("Pawn", board.fetchPieceOnPoint(new Point(3,2)).getPieceName());
        assertTrue(board.pieceOnPointColorEquals(new Point(3,2), ChessColor.WHITE));
        assertEquals(null, board.fetchPieceOnPoint(new Point(3,3)));
    }
}
