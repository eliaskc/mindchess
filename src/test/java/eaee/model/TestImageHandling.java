package eaee.model;

import chess.controller.ImageHandler;
import chess.model.Chess;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class TestImageHandling {
    Chess model;
    @Before
    public void init() {
        model = Chess.getInstance();
    }

    @Test
    public void testPieceImageFetch(){
        ImageHandler imageHandler = new ImageHandler();
        imageHandler.fetchPieceImages();
        ImageView test = new ImageView();
        test.setImage(new Image(getClass().getResourceAsStream("/chesspieces/black_rook.png")));
        assertTrue(imageHandler.pieceImages.get(0).getCssMetaData().equals(test.getCssMetaData()));
    }
}
