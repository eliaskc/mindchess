package eaee.model;

import chess.model.ChessFacade;
import chess.model.Movement;
import chess.model.Piece;
import chess.model.Square;
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
    Map<Square, Piece> boardMap = new HashMap<>();
    Movement movement = new Movement();

    @Before
    public void init() {
        model = new ChessFacade();
        model.createNewGame();
        boardMap = model.getCurrentBoardMap();
        movement.setBoardMap(boardMap);
    }

    @Test
    public void testCheckLegalQueen() {
        Square queenPosition = new Square(3,0);
        Piece queen = boardMap.get(queenPosition);
        List<Square> squares = movement.fetchLegalMoves(queen, queenPosition);

        assertTrue(squares.size() == 0);
    }

    @Test
    public void testCheckLegalBlackPawn() {
        Square pawnPosition = new Square(5,1);
        Piece pawn = boardMap.get(pawnPosition);
        List<Square> squares = movement.fetchLegalMoves(pawn, pawnPosition);

        List<Square> comparisonList = new ArrayList<>();
        comparisonList.add(new Square(5,2));
        comparisonList.add(new Square(5,3));
        //comparisonList.add(new Square(0,3)); not implemented yet

        assertTrue(squares.equals(comparisonList));
    }

    @Test
    public void testCheckLegalWhitePawn() {
        Square pawnPosition = new Square(0,6);
        Piece pawn = boardMap.get(pawnPosition);
        List<Square> squares = movement.fetchLegalMoves(pawn, pawnPosition);

        List<Square> comparisonList = new ArrayList<>();
        comparisonList.add(new Square(0,5));
        comparisonList.add(new Square(0,4));
        //comparisonList.add(new Square(0,4)); not implemented yet

        assertTrue(squares.equals(comparisonList));
    }

    @Test
    public void testCheckLegalKnight() {
        Square knightPosition = new Square(1,0);
        Piece knight = boardMap.get(knightPosition);
        List<Square> squares = movement.fetchLegalMoves(knight, knightPosition);

        assertTrue(squares.get(0).getX() == 2 && squares.get(0).getY() == 2);
    }
}
