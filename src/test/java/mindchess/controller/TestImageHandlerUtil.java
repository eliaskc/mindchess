package mindchess.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import mindchess.model.Board;
import mindchess.model.ChessFacade;
import mindchess.model.Square;
import mindchess.model.enums.ChessColor;
import mindchess.model.enums.PlayerType;
import org.junit.Before;
import org.junit.Test;

import static mindchess.model.enums.PieceType.BISHOP;
import static mindchess.model.enums.PieceType.KNIGHT;
import static org.junit.Assert.assertEquals;

/**
 * Tests the ImageHandler class
 *
 * Only able to test the lists that are returned from methods with our knowlage of tests
 */
public class TestImageHandlerUtil {

    ImageHandlerUtil imageHandlerUtil = new ImageHandlerUtil();
    ChessFacade model;
    Board board;

    @Before
    public void init() {
        model = new ChessFacade();
        model.createNewGame("White", "Black", PlayerType.HUMAN, PlayerType.HUMAN, 180);
        board = model.getCurrentBoard();

        MenuController menuController = new MenuController();
        MindchessController mindchessController = new MindchessController();

        imageHandlerUtil.setModel(model);
    }

    /**
     * Tests that the size of the DeadPieceImages list is correct
     */
    @Test
    public void testFetchDeadPieceImagesSize() {
        model.handleBoardInput(4,6);
        model.handleBoardInput(4,4);

        model.handleBoardInput(3,1);
        model.handleBoardInput(3,3);

        model.handleBoardInput(4,4);
        model.handleBoardInput(3,3);

        model.handleBoardInput(2,0);
        model.handleBoardInput(6,4);

        model.handleBoardInput(3,7);
        model.handleBoardInput(6,4);

        assertEquals(2, imageHandlerUtil.fetchDeadPieceImages(ChessColor.BLACK).size());
    }

}
