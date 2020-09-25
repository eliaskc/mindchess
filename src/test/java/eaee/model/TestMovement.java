package eaee.model;

import chess.model.ChessFacade;
import chess.model.Movement;
import chess.model.Piece;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class TestMovement {
    ChessFacade model;
    Map<Point, Piece> boardMap = new HashMap<>();
    Movement movement = new Movement();

    @Before
    public void init() {
        model = ChessFacade.getInstance();
        boardMap = model.getGame().getBoard().getBoardMap();
        movement.setBoardMap(boardMap);
    }

    @Test
    public void testCheckLegalQueen() {
        Point queenPosition = new Point(3,0);
        Piece queen = boardMap.get(queenPosition);
        List<Point> points = movement.pieceMoveDelegation(queen, queenPosition);

        assertTrue(points.size() == 0);
    }

    @Test
    public void testCheckLegalBlackPawn() {
        Point pawnPosition = new Point(5,1);
        Piece pawn = boardMap.get(pawnPosition);
        List<Point> points = movement.pieceMoveDelegation(pawn, pawnPosition);

        List<Point> comparisonList = new ArrayList<>();
        comparisonList.add(new Point(5,2));
        //comparisonList.add(new Point(0,3)); not implemented yet

        assertTrue(points.equals(comparisonList));
    }

    @Test
    public void testCheckLegalWhitePawn() {
        Point pawnPosition = new Point(0,6);
        Piece pawn = boardMap.get(pawnPosition);
        List<Point> points = movement.pieceMoveDelegation(pawn, pawnPosition);

        List<Point> comparisonList = new ArrayList<>();
        comparisonList.add(new Point(0,5));
        //comparisonList.add(new Point(0,4)); not implemented yet

        assertTrue(points.equals(comparisonList));
    }

    @Test
    public void testCheckLegalKnight() {
        Point knightPosition = new Point(1,0);
        Piece knight = boardMap.get(knightPosition);
        List<Point> points = movement.pieceMoveDelegation(knight, knightPosition);

        assertTrue(points.get(0).x == 2 && points.get(0).y == 2);
    }
}
