package eaee.model;

import chess.model.*;
import chess.model.pieces.Piece;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestChess {
    Chess chessModel;
    @Before
    public void init(){
        chessModel = Chess.getInstance();
        chessModel.getBoard().getPieces().add(PieceFactory.createQueen(chessModel.getBoard().getSquares()[0][0],true, Color.WHITE));
    }

    @Test
    public void testSingleton(){
        Chess newModel = Chess.getInstance();
        assertTrue(newModel.equals(chessModel));
    }

    @Test
    public void testImage(){
        chessModel.getBoard().getPieces().get(0).fetchImage();
        ImageView imageView = new ImageView();
        imageView.setImage(new Image(getClass().getResourceAsStream("/chessPieces/white_queen.png")));
        assertTrue(chessModel.getBoard().getPieces().get(0).getPieceImage().getCssMetaData().equals(imageView.getCssMetaData()));
    }

    @Test
    public void testMovePiece(){
        chessModel.clickSquare(377, 117);
        chessModel.clickSquare(452, 108);
        assertTrue(chessModel.getBoard().getPieces().get(0).getPosition().getCoordinatesX() == 1);
        assertTrue(chessModel.getBoard().getPieces().get(0).getPosition().getCoordinatesY() == 0);
    }
}
