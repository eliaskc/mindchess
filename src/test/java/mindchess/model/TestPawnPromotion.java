package mindchess.model;

import mindchess.model.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class TestPawnPromotion {
    ChessFacade model;
    Board board;

    @Before
    public void init() {
        model = new ChessFacade();
        model.createNewGame("White", "Black", PlayerType.HUMAN, PlayerType.HUMAN, 180);
        board = model.getCurrentBoard();
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

        assertEquals(PieceType.QUEEN, board.fetchPieceOnSquare(new Square(4,0)).getPieceType());
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

        assertEquals(PieceType.PAWN, board.fetchPieceOnSquare(new Square(4,1)).getPieceType());
    }
}
