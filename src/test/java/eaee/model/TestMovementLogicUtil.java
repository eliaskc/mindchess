package eaee.model;

import chess.model.Board;
import chess.model.ChessFacade;
import chess.model.pieces.IPiece;
import chess.model.util.MovementLogicUtil;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class TestMovementLogicUtil {
    ChessFacade model;
    Board board;

    @Before
    public void init() {
        model = new ChessFacade();
        model.createNewGame();
        board = model.getCurrentBoard();
    }

    @Test
    public void testCheckLegalQueen() {
        Point queenPosition = new Point(3,0);
        IPiece queen = board.fetchPieceOnPoint(queenPosition);
        List<Point> points = queen.getMoveDelegate().fetchMoves(board, queenPosition, queen.getHasMoved());

        assertTrue(points.size() == 0);
    }

    @Test
    public void testCheckLegalBlackPawn() {
        Point pawnPosition = new Point(5,1);
        IPiece pawn = board.fetchPieceOnPoint(pawnPosition);
        List<Point> points = pawn.getMoveDelegate().fetchMoves(board, pawnPosition, pawn.getHasMoved());

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
        List<Point> points = pawn.getMoveDelegate().fetchMoves(board, pawnPosition, pawn.getHasMoved());

        List<Point> comparisonList = new ArrayList<>();
        comparisonList.add(new Point(0,5));
        comparisonList.add(new Point(0,4));

        assertTrue(points.equals(comparisonList));
    }

    @Test
    public void testCheckLegalWhitePawnAfterMove() {
        Point pawnPosition = new Point(0,6);
        IPiece pawn = board.fetchPieceOnPoint(pawnPosition);
        pawn.setHasMoved(true);
        List<Point> points = pawn.getMoveDelegate().fetchMoves(board, pawnPosition, pawn.getHasMoved());

        List<Point> comparisonList = new ArrayList<>();
        comparisonList.add(new Point(0,5));

        assertTrue(points.equals(comparisonList));
    }

    @Test
    public void testCheckLegalKnight() {
        Point knightPosition = new Point(1,0);
        IPiece knight = board.fetchPieceOnPoint(knightPosition);
        List<Point> points = knight.getMoveDelegate().fetchMoves(board, knightPosition, knight.getHasMoved());

        assertTrue(points.get(0).x == 2 && points.get(0).y == 2);
    }
}
