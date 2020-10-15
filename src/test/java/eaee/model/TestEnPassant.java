package eaee.model;

import chess.model.ChessFacade;
import chess.model.ChessColor;
import chess.model.Movement;
import chess.model.Piece;
import chess.model.PieceType;
import org.junit.Before;
import org.junit.Test;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;


public class TestEnPassant {
    ChessFacade model;
    Map<Point, Piece> boardMap = new HashMap<>();
    Movement movement = new Movement();

    @Before
    public void init() {
        model = new ChessFacade();
        model.createNewGame();
        boardMap = model.getCurrentGame().getBoard().getBoardMap();
        movement.setBoardMap(boardMap);
    }

    @Test
    public void testEnPassantPossible() {
        //En Passant setup
        model.handleBoardInput(4,6);
        model.handleBoardInput(4,4);

        model.handleBoardInput(0,1);
        model.handleBoardInput(0,2);

        model.handleBoardInput(4,4);
        model.handleBoardInput(4,3);

        model.handleBoardInput(3,1);
        model.handleBoardInput(3,3);

        //The actual en passant
        model.handleBoardInput(4,3);
        model.handleBoardInput(3,2);

        assertEquals(PieceType.PAWN, boardMap.get(new Point(3,2)).getPieceType());
        assertEquals(ChessColor.WHITE, boardMap.get(new Point(3,2)).getColor());
        assertEquals(null, boardMap.get(new Point(3,3)));
    }
}
