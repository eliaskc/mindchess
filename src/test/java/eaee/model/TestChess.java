package eaee.model;

import chess.controller.ChessController;
import chess.model.*;
import chess.model.Piece;
import javafx.event.Event;
import javafx.scene.image.*;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class TestChess {
    Chess model;
    ChessController chessController = new ChessController();
    @Before
    public void init() {
        model = Chess.getInstance();
    }

    @Test
    public void testMove() {
        Piece testPiece = model.getBoard().getPieces().get(0);
        assertTrue(testPiece.getSquare().getCoordinatesX() == 0);
        assertTrue(testPiece.getSquare().getCoordinatesY() == 0);

        int x1 = chessController.translateX(345);
        int y1 = chessController.translateY(65);
        model.handleBoardClick(x1, y1);
        int x2 = chessController.translateX(425);
        int y2 = chessController.translateY(145);
        model.handleBoardClick(x2, y2);

        assertTrue(testPiece.getSquare().getCoordinatesX() == 1);
        assertTrue(testPiece.getSquare().getCoordinatesY() == 1);
    }

    /**
     * test the placeAllPieces method
     */
    @Test
    public void testPlaceAllPieces(){
        for (int i = 0; i < 16; i++) {
            assertEquals(Color.BLACK,model.getBoard().getPieces().get(i).getColor());
        }
        for (int i = 16; i < 32; i++) {
            assertEquals(Color.WHITE,model.getBoard().getPieces().get(i).getColor());
        }
    }

    /*@Test
    public void testTranslate() {
        chessController.updateSquareDimensions();
        int x = chessController.translateX(425);
        int y = chessController.translateY(145);
        assertTrue(x==1);
        assertTrue(y==1);
    }*/
}
