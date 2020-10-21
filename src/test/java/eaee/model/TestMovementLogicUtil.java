package eaee.model;

import mindchess.model.Board;
import mindchess.model.ChessFacade;
import mindchess.model.Square;
import mindchess.model.pieces.IPiece;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
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
        Square queenPosition = new Square(3,0);
        IPiece queen = board.fetchPieceOnSquare(queenPosition);
        List<Square> squares = queen.getMoveDelegate().fetchMoves(board, queenPosition, queen.getHasMoved(), false);

        assertEquals(0, squares.size());
    }

    @Test
    public void testCheckLegalBlackPawn() {
        Square pawnPosition = new Square(5,1);
        IPiece pawn = board.fetchPieceOnSquare(pawnPosition);
        List<Square> squares = pawn.getMoveDelegate().fetchMoves(board, pawnPosition, pawn.getHasMoved(), false);

        List<Square> comparisonList = new ArrayList<>();
        comparisonList.add(new Square(5,2));
        comparisonList.add(new Square(5,3));
        //comparisonList.add(new Point(0,3)); not implemented yet

        assertEquals(squares, comparisonList);
    }

    @Test
    public void testCheckLegalWhitePawn() {
        Square pawnPosition = new Square(0,6);
        IPiece pawn = board.fetchPieceOnSquare(pawnPosition);
        List<Square> squares = pawn.getMoveDelegate().fetchMoves(board, pawnPosition, pawn.getHasMoved(), false);

        List<Square> comparisonList = new ArrayList<>();
        comparisonList.add(new Square(0,5));
        comparisonList.add(new Square(0,4));

        assertEquals(squares, comparisonList);
    }

    @Test
    public void testCheckLegalWhitePawnAfterMove() {
        Square pawnPosition = new Square(0,6);
        IPiece pawn = board.fetchPieceOnSquare(pawnPosition);
        pawn.setHasMoved(true);
        List<Square> squares = pawn.getMoveDelegate().fetchMoves(board, pawnPosition, pawn.getHasMoved(), false);

        List<Square> comparisonList = new ArrayList<>();
        comparisonList.add(new Square(0,5));

        assertEquals(squares, comparisonList);
    }

    @Test
    public void testCheckLegalKnight() {
        Square knightPosition = new Square(1,0);
        IPiece knight = board.fetchPieceOnSquare(knightPosition);
        List<Square> squares = knight.getMoveDelegate().fetchMoves(board, knightPosition, knight.getHasMoved(), false);

        assertTrue(squares.get(0).getX() == 2 && squares.get(0).getY() == 2);
    }
}
