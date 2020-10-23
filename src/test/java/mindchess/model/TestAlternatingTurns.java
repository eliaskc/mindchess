package mindchess.model;

import mindchess.model.enums.PlayerType;
import mindchess.model.pieces.IPiece;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Tests that the players have alternating turns
 */
public class TestAlternatingTurns {
    ChessFacade model;
    Board board;

    @Before
    public void init() {
        model = new ChessFacade();
        model.createNewGame("White", "Black", PlayerType.HUMAN, PlayerType.HUMAN, 180);
        board = model.getCurrentBoard();
    }

    /**
     * Tests that the player switches after input
     */
    @Test
    public void testAlternatingTurns(){
        String p1 = model.getCurrentWhitePlayerName();
        String p2 = model.getCurrentBlackPlayerName();
        model.handleBoardInput(0,6);
        model.handleBoardInput(0, 5);

        String currPlayer = model.getCurrentPlayerName();
        assertTrue(currPlayer.equals(p2) && !currPlayer.equals(p1));
    }
}
