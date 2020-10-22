package mindchess.model;

import mindchess.model.enums.PlayerType;
import org.junit.Before;
import org.junit.Test;

import static mindchess.model.enums.PieceType.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class TestKingMoveIntoCheck {

    ChessFacade model;
    Board board;

    @Before
    public void init() {
        model = new ChessFacade();
        model.createNewGame("White", "Black", PlayerType.HUMAN, PlayerType.HUMAN, 180);
        board = model.getCurrentBoard();
    }

    @Test
    public void testKingMovingIntoCheck() {
        model.handleBoardInput(6,7);
        model.handleBoardInput(5,5);

        model.handleBoardInput(4,1);
        model.handleBoardInput(4,3);

        model.handleBoardInput(5,5);
        model.handleBoardInput(6,3);

        model.handleBoardInput(4,0);
        model.handleBoardInput(4,1);

        model.handleBoardInput(4,6);
        model.handleBoardInput(4,5);

        //King moves into check
        model.handleBoardInput(4,1);
        model.handleBoardInput(4,2);

        assertEquals(KING, board.fetchPieceOnSquare(new Square(4, 1)).getPieceType());
    }
}
