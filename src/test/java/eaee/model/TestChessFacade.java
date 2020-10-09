package eaee.model;

import chess.model.*;
import chess.model.ChessColor;
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
        model.createNewGame();
    }

    @Test
    public void testMoveAndPly() {
        Map<Point, Piece> boardMap = model.getGame().getBoard().getBoardMap();
        Point p1 = new Point(0,6);
        Point p2 = new Point(0,5);

        Piece testPieceBefore = boardMap.get(p1);

        model.handleBoardClick(0,6);
        model.handleBoardClick(0,5);

        Piece testPieceAfter = boardMap.get(p2);

        Ply ply = new Ply(p1,p2,testPieceAfter, model.getPlayerWhite());

        assertEquals(testPieceBefore, testPieceAfter);
        assertEquals(model.getGame().getPlies().get(0).getMovedFrom(), ply.getMovedFrom());
        assertEquals(model.getGame().getPlies().get(0).getMovedTo(), ply.getMovedTo());
        assertEquals(model.getGame().getPlies().get(0).getMovedPiece(), ply.getMovedPiece());
        assertEquals(model.getGame().getPlies().get(0).getPlayer(), ply.getPlayer());
    }

    /**
     * test the placeAllPieces method
     */
    @Test
    public void testPlaceAllPieces(){
        Map<Point, Piece> boardMap = model.getGame().getBoard().getBoardMap();

        assertTrue(boardMap.get(new Point(0,0)).getColor() == ChessColor.BLACK && boardMap.get(new Point(0,0)).getPieceType() == PieceType.ROOK);
        assertTrue(boardMap.get(new Point(6,6)).getColor() == ChessColor.WHITE && boardMap.get(new Point(6,6)).getPieceType() == PieceType.PAWN);
        assertTrue(boardMap.get(new Point(4,7)).getColor() == ChessColor.WHITE && boardMap.get(new Point(4,7)).getPieceType() == PieceType.KING);
    }
}