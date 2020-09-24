package eaee.model;

import chess.controller.ImageHandler;
import chess.model.ChessFacade;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestImageHandling {
    ChessFacade model;
    ImageHandler imageHandler;
    @Before
    public void init() {
        model = ChessFacade.getInstance();
        imageHandler = new ImageHandler();
        imageHandler.fetchPieceImages();
    }

    @Test
    public void testPieceImageFetch(){


        ImageView test1 = new ImageView();
        test1.setImage(new Image(getClass().getResourceAsStream("/chesspieces/black_rook.png")));
        assertTrue(imageHandler.getPieceImages().get(0).getCssMetaData().equals(test1.getCssMetaData()));

        ImageView test2 = new ImageView();
        test2.setImage(new Image(getClass().getResourceAsStream("/chesspieces/black_bishop.png")));
        assertTrue(imageHandler.getPieceImages().get(2).getCssMetaData().equals(test2.getCssMetaData()));
    }

    @Test
    public void testUpdateCoordinates() {
        ImageView test = imageHandler.getPieceImages().get(0);
        assertTrue(test.getX() == 5);
        assertTrue(test.getY() == 5);

        //hardcoded test for moving piece, also outdated (square, not point)
//        model.getBoard().getPieces().get(0).setSquare(model.getBoard().getSquares()[2][2]);

        imageHandler.updateImageCoordinates();

        assertTrue(test.getX() == 2 * imageHandler.getSquareDimension() + 5);
        assertTrue(test.getY() == 2 * imageHandler.getSquareDimension() + 5);
    }
}