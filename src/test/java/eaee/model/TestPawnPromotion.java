package eaee.model;

import chess.model.ChessFacade;
import chess.model.PieceType;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import static org.junit.Assert.assertEquals;

public class TestPawnPromotion {
    private ChessFacade model;

    @Before
    public void init() {
        model = new ChessFacade();
        model.createNewGame();
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

        assertEquals(PieceType.QUEEN, model.getCurrentBoardMap().get(new Point(4,0)).getPieceType());
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

        assertEquals(model.getCurrentBoardMap().get(new Point(4,1)).getPieceType(), PieceType.PAWN);
    }
}
