package eaee.model;

import chess.controller.ChessController;
import chess.model.*;
import chess.model.Color;
import chess.model.Piece;
import org.junit.Before;
import org.junit.Test;


import java.awt.*;
import java.util.Map;

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
        Map<Point, Piece> boardMap = model.getBoard().getBoardMap();
        Piece testPieceBefore = boardMap.get(new Point(0,1));

        model.handleBoardClick(0,1);
        model.handleBoardClick(0,2);

        Piece testPieceAfter = boardMap.get(new Point(0,2));

        assertTrue(testPieceAfter.equals(testPieceBefore));
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