package eaee.model;

import chess.controller.ChessController;
import chess.model.*;
import chess.model.Piece;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class TestChessFacade {
    private ChessFacade model;
    private ChessController chessController = new ChessController();

    @Before
    public void init() {
        model = ChessFacade.getInstance();
    }

    @Test
    public void testMove() {
        Piece testPiece = model.getBoard().getPieces().get(0);
        assertTrue(testPiece.getSquare().getCoordinatesX() == 0);
        assertTrue(testPiece.getSquare().getCoordinatesY() == 0);

        model.handleBoardClick(0,0);
        model.handleBoardClick(1,1);

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
}
