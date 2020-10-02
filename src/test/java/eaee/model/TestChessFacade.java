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

    @Before
    public void init() {
        model = new ChessFacade();
    }

    @Test
    public void testMove() {
        Map<Point, Piece> boardMap = model.getGame().getBoard().getBoardMap();
        Piece testPieceBefore = boardMap.get(new Point(0,6));

        model.handleBoardClick(0,6);
        model.handleBoardClick(0,5);

        Piece testPieceAfter = boardMap.get(new Point(0,5));

        assertEquals(testPieceBefore, testPieceAfter);
    }

    /**
     * test the placeAllPieces method
     */
    @Test
    public void testPlaceAllPieces(){
        Map<Point, Piece> boardMap = model.getGame().getBoard().getBoardMap();

        assertTrue(boardMap.get(new Point(0,0)).getColor() == Color.BLACK && boardMap.get(new Point(0,0)).getPieceType() == PieceType.ROOK);
        assertTrue(boardMap.get(new Point(6,6)).getColor() == Color.WHITE && boardMap.get(new Point(6,6)).getPieceType() == PieceType.PAWN);
        assertTrue(boardMap.get(new Point(4,7)).getColor() == Color.WHITE && boardMap.get(new Point(4,7)).getPieceType() == PieceType.KING);
    }
}