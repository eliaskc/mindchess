package chess.controller;

import chess.model.ChessFacade;
import chess.model.Piece;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.util.*;
import java.util.List;

import static chess.model.Color.*;

public class ImageHandler {
    private List<ImageView> pieceImages = new ArrayList<>();
    private Map<Piece, ImageView> pieceImageViewMap = new HashMap<>();
    private ChessFacade model = ChessFacade.getInstance();
    private Map<Point, Piece> boardMap = model.getBoard().getBoardMap();

    private double squareDimension;

    public List<ImageView> getPieceImages() {
        return pieceImages;
    }

    public List<ImageView> fetchPieceImages() {
        for (Map.Entry<Point,Piece> entry : boardMap.entrySet()) {
            String imageURL = "";
            if (entry.getValue() == null) {
                break;
            }
            switch (entry.getValue().getPieceType()) {
                case ROOK:
                    if (entry.getValue().getColor().equals(BLACK)) {
                        imageURL = "/chesspieces/black_rook.png";
                    } else if(entry.getValue().getColor().equals(WHITE)) {
                        imageURL = "/chesspieces/white_rook.png";
                    }
                    break;
                case BISHOP:
                    if (entry.getValue().getColor().equals(BLACK)) {
                        imageURL = "/chesspieces/black_bishop.png";
                    } else if(entry.getValue().getColor().equals(WHITE)) {
                        imageURL = "/chesspieces/white_bishop.png";
                    }
                    break;
                case KNIGHT:
                    if (entry.getValue().getColor().equals(BLACK)) {
                        imageURL = "/chesspieces/black_knight.png";
                    } else if(entry.getValue().getColor().equals(WHITE)) {
                        imageURL = "/chesspieces/white_knight.png";
                    }
                    break;
                case QUEEN:
                    if (entry.getValue().getColor().equals(BLACK)) {
                        imageURL = "/chesspieces/black_queen.png";
                    } else if(entry.getValue().getColor().equals(WHITE)) {
                        imageURL = "/chesspieces/white_queen.png";
                    }
                    break;
                case KING:
                    if (entry.getValue().getColor().equals(BLACK)) {
                        imageURL = "/chesspieces/black_king.png";
                    } else if(entry.getValue().getColor().equals(WHITE)) {
                        imageURL = "/chesspieces/white_king.png";
                    }
                    break;
                case PAWN:
                    if (entry.getValue().getColor().equals(BLACK)) {
                        imageURL = "/chesspieces/black_pawn.png";
                    } else if(entry.getValue().getColor().equals(WHITE)) {
                        imageURL = "/chesspieces/white_pawn.png";
                    }
                    break;
            }
            ImageView pieceImage = new ImageView();
            pieceImage.setImage(new Image(getClass().getResourceAsStream(imageURL)));
            pieceImage.setFitWidth(squareDimension-10);
            pieceImage.setFitHeight(squareDimension-10);

            pieceImage.setX(entry.getKey().x * squareDimension + 5);
            pieceImage.setY(entry.getKey().y * squareDimension + 5);

            pieceImages.add(pieceImage);
            pieceImageViewMap.put(entry.getValue(), pieceImage);
        }
        return pieceImages;
    }

    public void updateImageCoordinates() {
        for(Map.Entry<Piece, ImageView> entry : pieceImageViewMap.entrySet()) {
            entry.getValue().setX(getKeyByValue(model.getBoard().getBoardMap(), entry.getKey()).x * squareDimension + 5);
            entry.getValue().setY(getKeyByValue(model.getBoard().getBoardMap(), entry.getKey()).y * squareDimension + 5);
        }
    }

    public static <T, E> T getKeyByValue(Map<T, E> map, E value) {
        for (Map.Entry<T, E> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue())) {
                return entry.getKey();
            }
        }
        return null;
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
        for (Point square : model.getBoard().getMockLegalSquares()) {
            ImageView imageView = new ImageView();
            imageView.setImage(new Image(getClass().getResourceAsStream("/legalMove.png")));
            imageView.setFitWidth(squareDimension - 50);
            imageView.setFitHeight(squareDimension - 50);

            imageView.setX(square.x * squareDimension + 25);
            imageView.setY(square.y * squareDimension + 25);

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
