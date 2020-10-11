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
        model.handleBoardClick(5,6);
        model.handleBoardClick(5,4);

        model.handleBoardClick(4, 1);
        model.handleBoardClick(4, 3);

        model.handleBoardClick(5,4);
        model.handleBoardClick(4,3);

        model.handleBoardClick(5,1);
        model.handleBoardClick(5,2);

        model.handleBoardClick(4,3);
        model.handleBoardClick(4,2);

        model.handleBoardClick(4, 0);
        model.handleBoardClick(5, 1);

        model.handleBoardClick(4,2);
        model.handleBoardClick(4,1);

        model.handleBoardClick(5,1);
        model.handleBoardClick(6,2);

        //Execution
        model.handleBoardClick(4,1);
        model.handleBoardClick(4,0);

        model.getCurrentGame().pawnPromotion(PieceType.QUEEN);

        assertEquals(model.getCurrentGame().getBoard().getBoardMap().get(new Point(4,0)).getPieceType(), PieceType.QUEEN);
    }

    @Test
    public void testPawnPromotionFail() {
        //Setup
        model.handleBoardClick(5,6);
        model.handleBoardClick(5,4);

        model.handleBoardClick(4, 1);
        model.handleBoardClick(4, 3);

        model.handleBoardClick(5,4);
        model.handleBoardClick(4,3);

        model.handleBoardClick(5,1);
        model.handleBoardClick(5,2);

        model.handleBoardClick(4,3);
        model.handleBoardClick(4,2);

        model.handleBoardClick(4, 0);
        model.handleBoardClick(5, 1);

        //Execution. Will fail since the pawn won't be in a spot where it can be promoted
        model.handleBoardClick(4,2);
        model.handleBoardClick(4,1);

        model.getCurrentGame().pawnPromotion(PieceType.QUEEN);

        assertEquals(model.getCurrentGame().getBoard().getBoardMap().get(new Point(4,1)).getPieceType(), PieceType.PAWN);
    }
}
