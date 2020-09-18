package eaee.model;

import chess.model.*;
import chess.model.Piece;
import javafx.scene.image.*;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestChess {
    Chess model;
    @Before
    public void init() {
        model = Chess.getInstance();
    }

    @Test
    public void testMove() {
        Piece testPiece = model.getBoard().getPieces().get(0);
        assertTrue(testPiece.getSquare().getCoordinatesX() == 0);
        assertTrue(testPiece.getSquare().getCoordinatesY() == 0);

        model.handleBoardClick(345, 65);
        model.handleBoardClick(425, 145);

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

    /**
     * tests the fetchimages method
     */
    @Test
    public void testFetchImages(){
        ImageView image = model.getBoard().getPieceImages().get(0);
        assertEquals(model.getBoard().getPieces().get(0).getPieceImage(),image);
    }
}
