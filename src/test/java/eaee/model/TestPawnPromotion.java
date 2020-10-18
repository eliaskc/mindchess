package eaee.model;

import chess.model.Board;
import chess.model.ChessFacade;
import chess.model.pieces.PieceMovementLogic;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class TestPawnPromotion {
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
    public void testPawnPromotionSuccess() {
        //Setup
        model.handleBoardInput(5,6);
        model.handleBoardInput(5,4);

        model.handleBoardInput(4, 1);
        model.handleBoardInput(4, 3);

        model.handleBoardInput(5,4);
        model.handleBoardInput(4,3);

        model.handleBoardInput(5,1);
        model.handleBoardInput(5,2);

        model.handleBoardInput(4,3);
        model.handleBoardInput(4,2);

        model.handleBoardInput(4, 0);
        model.handleBoardInput(5, 1);

        model.handleBoardInput(4,2);
        model.handleBoardInput(4,1);

        model.handleBoardInput(5,1);
        model.handleBoardInput(6,2);

        //Execution
        model.handleBoardInput(4,1);
        model.handleBoardInput(4,0);

        model.handleBoardInput(20,0);

        assertEquals("Queen", board.fetchPieceOnPoint(new Point(4,0)).getPieceName());
    }

    @Test
    public void testPawnPromotionFail() {
        //Setup
        model.handleBoardInput(5,6);
        model.handleBoardInput(5,4);

        model.handleBoardInput(4, 1);
        model.handleBoardInput(4, 3);

        model.handleBoardInput(5,4);
        model.handleBoardInput(4,3);

        model.handleBoardInput(5,1);
        model.handleBoardInput(5,2);

        model.handleBoardInput(4,3);
        model.handleBoardInput(4,2);

        model.handleBoardInput(4, 0);
        model.handleBoardInput(5, 1);

        //Execution. Will fail since the pawn won't be in a spot where it can be promoted
        model.handleBoardInput(4,2);
        model.handleBoardInput(4,1);

        model.handleBoardInput(0,1);

        assertEquals("Pawn", board.fetchPieceOnPoint(new Point(4,1)).getPieceName());
    }
}
