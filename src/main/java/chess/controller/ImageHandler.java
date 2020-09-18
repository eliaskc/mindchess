package chess.controller;

import chess.model.Chess;
import chess.model.Color;
import chess.model.Piece;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

public class ImageHandler {
    public List<ImageView> pieceImages = new ArrayList<>();
    Chess model = Chess.getInstance();

    public void fetchPieceImages() {
        for (Piece p : model.getBoard().getPieces()) {
            String imageURL = "";
            switch (p.getPieceType()) {
                case ROOK:
                    if (p.getColor().equals(Color.BLACK)) {
                        imageURL = "/chesspieces/black_rook.png";
                    }
            }
            ImageView pieceImage = new ImageView();
            pieceImage.setImage(new Image(getClass().getResourceAsStream(imageURL)));
            pieceImages.add(pieceImage);
        }
    }
}
