package mindchess.model;

import mindchess.model.enums.ChessColor;
import mindchess.model.enums.PlayerType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Test GameStateAIPlayer class
 *
 * @author Erik Wessman, Elias Carlson, Elias Hallberg, Arvid Holmqvist
 */
public class TestAIPlayer {
    ChessFacade model;

    @Before
    public void init() {
        model = new ChessFacade();
    }

    @Test
    public void testAIPlayerStateSwitchesPlayer() {
        model.createNewGame("White", "Black", PlayerType.HUMAN, PlayerType.CPU_LEVEL2, 180);

        model.handleBoardInput(0,6);
        model.handleBoardInput(0,4);

        assertEquals(ChessColor.WHITE, model.getCurrentPlayerColor());
    }

    @Test
    public void testAIPlayerLevel1MakesMove() {
        model.createNewGame("White", "Black", PlayerType.HUMAN, PlayerType.CPU_LEVEL1, 180);

        model.handleBoardInput(0,6);
        model.handleBoardInput(0,4);

        assertEquals(ChessColor.BLACK, model.getCurrentGamePlies().get(1).getMovedPiece().getColor());
    }

    @Test
    public void testAIPlayerLevel2MakesMove() {
        model.createNewGame("White", "Black", PlayerType.HUMAN, PlayerType.CPU_LEVEL2, 180);

        model.handleBoardInput(0,6);
        model.handleBoardInput(0,4);

        assertEquals(ChessColor.BLACK, model.getCurrentGamePlies().get(1).getMovedPiece().getColor());
    }
}
