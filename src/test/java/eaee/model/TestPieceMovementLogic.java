package eaee.model;

import chess.model.Board;
import chess.model.ChessFacade;
import chess.model.pieces.IPiece;
import chess.model.pieces.PieceMovementLogic;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class TestPieceMovementLogic {
    ChessFacade model;
    Board board;
    PieceMovementLogic pieceMovementLogic = PieceMovementLogic.getInstance();

    @Before
    public void init() {
        model = new ChessFacade();
        model.createNewGame();
        board = model.getCurrentBoard();
        pieceMovementLogic.setBoard(board);
    }

    @Test
    public void testCheckLegalQueen() {
        Point queenPosition = new Point(3,0);
        IPiece queen = board.fetchPieceOnPoint(queenPosition);
        List<Point> points = queen.fetchLegalMoves(queenPosition);

        assertTrue(points.size() == 0);
    }

    @Test
    public void testCheckLegalBlackPawn() {
        Point pawnPosition = new Point(5,1);
        IPiece pawn = board.fetchPieceOnPoint(pawnPosition);
        List<Point> points = pawn.fetchLegalMoves(pawnPosition);

        List<Point> comparisonList = new ArrayList<>();
        comparisonList.add(new Point(5,2));
        comparisonList.add(new Point(5,3));
        //comparisonList.add(new Point(0,3)); not implemented yet

        assertTrue(points.equals(comparisonList));
    }

    @Test
    public void testCheckLegalWhitePawn() {
        Point pawnPosition = new Point(0,6);
        IPiece pawn = board.fetchPieceOnPoint(pawnPosition);
        List<Point> points = pawn.fetchLegalMoves(pawnPosition);

        List<Point> comparisonList = new ArrayList<>();
        comparisonList.add(new Point(0,5));
        comparisonList.add(new Point(0,4));
        //comparisonList.add(new Point(0,4)); not implemented yet

        assertTrue(points.equals(comparisonList));
    }

    @Test
    public void testCheckLegalKnight() {
        Point knightPosition = new Point(1,0);
        IPiece knight = board.fetchPieceOnPoint(knightPosition);
        List<Point> points = knight.fetchLegalMoves(knightPosition);

        assertTrue(points.get(0).x == 2 && points.get(0).y == 2);
    }
}
