package eaee.model;

import chess.model.*;
import chess.model.ChessColor;
import chess.model.pieces.IPiece;
import org.junit.Before;
import org.junit.Test;


import java.awt.*;
import java.util.Map;

import static chess.model.PieceType.*;
import static org.junit.Assert.*;

public class TestChessFacade {
    private ChessFacade model;
    Board board;

    @Before
    public void init() {
        model = new ChessFacade();
        model.createNewGame();
        board = model.getCurrentBoard();
    }

    @Test
    public void testMoveAndPly() {
        Map<Point, IPiece> boardMap = model.getCurrentBoardMap();
        Point p1 = new Point(0,6);
        Point p2 = new Point(0,5);

        IPiece testIPieceBefore = boardMap.get(p1);

        model.handleBoardInput(0,6);
        model.handleBoardInput(0,5);

        IPiece testIPieceAfter = boardMap.get(p2);

        Ply ply = new Ply("", p1,p2, testIPieceAfter,null,boardMap);

        assertEquals(testIPieceBefore, testIPieceAfter);
      
        assertEquals(model.getCurrentGamePlies().get(0).getMovedFrom(), ply.getMovedFrom());
        assertEquals(model.getCurrentGamePlies().get(0).getMovedTo(), ply.getMovedTo());
        assertEquals(model.getCurrentGamePlies().get(0).getMovedPiece(), ply.getMovedPiece());
    }

    @Test
    public void testPlaceAllPieces(){
        Map<Point, IPiece> boardMap = model.getCurrentBoardMap();

        assertTrue(board.pieceOnPointColorEquals(new Point(0,0), ChessColor.BLACK) && board.fetchPieceOnPoint(new Point(0,0)).getPieceType().equals(ROOK));
        assertTrue(board.pieceOnPointColorEquals(new Point(6,6), ChessColor.WHITE) && board.fetchPieceOnPoint(new Point(6,6)).getPieceType().equals(PAWN));
        assertTrue(board.pieceOnPointColorEquals(new Point(4,7), ChessColor.WHITE) && board.fetchPieceOnPoint(new Point(4,7)).getPieceType().equals(KING));
    }

    @Test
    public void testClickOutsideBoard() {
        model.handleBoardInput(10,11);
        model.handleBoardInput(10,12);
    }
}