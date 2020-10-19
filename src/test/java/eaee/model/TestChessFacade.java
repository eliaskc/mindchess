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
        Map<Square, Piece> boardMap = model.getCurrentBoardMap();
        Square p1 = new Square(0,6);
        Square p2 = new Square(0,5);

        Piece testPieceBefore = boardMap.get(p1);

        model.handleBoardInput(0,6);
        model.handleBoardInput(0,5);

        boardMap = model.getCurrentBoardMap();

        Piece testPieceAfter = boardMap.get(p2);

        Ply ply = new Ply("", p1,p2,testPieceAfter,null,boardMap);

        assertEquals(testPieceBefore, testPieceAfter);
      
        assertEquals(model.getCurrentGamePlies().get(0).getMovedFrom(), ply.getMovedFrom());
        assertEquals(model.getCurrentGamePlies().get(0).getMovedTo(), ply.getMovedTo());
        assertEquals(model.getCurrentGamePlies().get(0).getMovedPiece(), ply.getMovedPiece());
    }

    @Test
    public void testPlaceAllPieces(){
        Map<Square, Piece> boardMap = model.getCurrentBoardMap();

        assertTrue(boardMap.get(new Square(0,0)).getColor() == ChessColor.BLACK && boardMap.get(new Square(0,0)).getPieceType() == PieceType.ROOK);
        assertTrue(boardMap.get(new Square(6,6)).getColor() == ChessColor.WHITE && boardMap.get(new Square(6,6)).getPieceType() == PieceType.PAWN);
        assertTrue(boardMap.get(new Square(4,7)).getColor() == ChessColor.WHITE && boardMap.get(new Square(4,7)).getPieceType() == PieceType.KING);
    }

    @Test
    public void testClickOutsideBoard() {
        model.handleBoardInput(10,11);
        model.handleBoardInput(10,12);
    }
}