package eaee.model;

import chess.model.*;
import chess.model.pieces.Piece;
import javafx.fxml.FXML;
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

    @Test
    public void testPlaceAllPieces(){
    }
}
