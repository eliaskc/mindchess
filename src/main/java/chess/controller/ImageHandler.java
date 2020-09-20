package chess.controller;

import chess.model.ChessFacade;
import chess.model.Piece;
import chess.model.Square;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static chess.model.Color.*;

public class ImageHandler {
    private List<ImageView> pieceImages = new ArrayList<>();
    private Map<Piece, ImageView> pieceImageViewMap = new HashMap<>();
    private ChessFacade model = ChessFacade.getInstance();

    private double squareDimension;

    public List<ImageView> getPieceImages() {
        return pieceImages;
    }

    public List<ImageView> fetchPieceImages() {
        for (Piece p : model.getBoard().getPieces()) {
            String imageURL = "";
            switch (p.getPieceType()) {
                case ROOK:
                    if (p.getColor().equals(BLACK)) {
                        imageURL = "src/main/resources/chessPieces/black_rook.png";
                    } else if(p.getColor().equals(WHITE)) {
                        imageURL = "src/main/resources/chesspieces/white_rook.png";
                    }
                    break;
                case BISHOP:
                    if (p.getColor().equals(BLACK)) {
                        imageURL = "src/main/resources/chesspieces/black_bishop.png";
                    } else if(p.getColor().equals(WHITE)) {
                        imageURL = "src/main/resources/chesspieces/white_bishop.png";
                    }
                    break;
                case KNIGHT:
                    if (p.getColor().equals(BLACK)) {
                        imageURL = "src/main/resources/chesspieces/black_knight.png";
                    } else if(p.getColor().equals(WHITE)) {
                        imageURL = "src/main/resources/chesspieces/white_knight.png";
                    }
                    break;
                case QUEEN:
                    if (p.getColor().equals(BLACK)) {
                        imageURL = "src/main/resources/chesspieces/black_queen.png";
                    } else if(p.getColor().equals(WHITE)) {
                        imageURL = "src/main/resources/chesspieces/white_queen.png";
                    }
                    break;
                case KING:
                    if (p.getColor().equals(BLACK)) {
                        imageURL = "src/main/resources/chesspieces/black_king.png";
                    } else if(p.getColor().equals(WHITE)) {
                        imageURL = "src/main/resources/chesspieces/white_king.png";
                    }
                    break;
                case PAWN:
                    if (p.getColor().equals(BLACK)) {
                        imageURL = "src/main/resources/chesspieces/black_pawn.png";
                    } else if(p.getColor().equals(WHITE)) {
                        imageURL = "src/main/resources/chesspieces/white_pawn.png";
                    }
                    break;
            }
            ImageView pieceImage = new ImageView();
            pieceImage.setImage(new Image(getClass().getResourceAsStream(imageURL)));
            pieceImage.setFitWidth(squareDimension-10);
            pieceImage.setFitHeight(squareDimension-10);

            pieceImage.setX(p.getSquare().getCoordinatesX() * squareDimension + 5);
            pieceImage.setY(p.getSquare().getCoordinatesY() * squareDimension + 5);

            pieceImages.add(pieceImage);
            pieceImageViewMap.put(p, pieceImage);
        }
        return pieceImages;
    }


    public void updateImageCoordinates() {
        for(Map.Entry<Piece, ImageView> entry : pieceImageViewMap.entrySet()) {
            entry.getValue().setX(entry.getKey().getSquare().getCoordinatesX() * squareDimension + 5);
            entry.getValue().setY(entry.getKey().getSquare().getCoordinatesY() * squareDimension + 5);
        }
    }

    /**temp
     *
     * (does) finds and returns a list of images on all squares
     *
     * (should) finds and returns a list of images for the squares a piece is allowed to move to
     *
     * @return returns a list of images on all squares
     */
    List<ImageView> fetchLegalMoveImages() {
        List<ImageView> imageViews = new ArrayList<>();
        for (Square s : model.getBoard().getMockLegalSquares()) {
            ImageView imageView = new ImageView();
            imageView.setImage(new Image(getClass().getResourceAsStream("src/main/resources/legalMove.png")));
            imageView.setFitWidth(squareDimension - 50);
            imageView.setFitHeight(squareDimension - 50);

            imageView.setX(s.getCoordinatesX() * squareDimension + 25);
            imageView.setY(s.getCoordinatesY() * squareDimension + 25);

            imageViews.add(imageView);
        }
        return imageViews;
    }

    public double getSquareDimension() {
        return squareDimension;
    }

    public void setSquareDimension(double squareDimension) {
        this.squareDimension = squareDimension;
    }
}
