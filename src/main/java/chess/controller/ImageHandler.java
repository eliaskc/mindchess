package chess.controller;

import chess.model.ChessFacade;
import chess.model.Piece;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.util.*;
import java.util.List;

import static chess.model.Color.*;

/**
 * Is responsible for fetching the images from files and matching them with the right pieces
 */
public class ImageHandler {
    List<ImageView> blackImageViews = new ArrayList<>();
    List<ImageView> whiteImageViews = new ArrayList<>();
    private List<ImageView> pieceImages = new ArrayList<>();
    private Map<Piece, ImageView> pieceImageViewMap = new HashMap<>();
    private ChessFacade model;
    private Map<Point, Piece> boardMap;
    private double squareDimension;

    public List<ImageView> getBlackImageViews() {
        return blackImageViews;
    }

    public List<ImageView> getWhiteImageViews() {
        return whiteImageViews;
    }

    public List<ImageView> getPieceImages() {
        return pieceImages;
    }

    public void setModel(ChessFacade model) {
        this.model = model;
    }

    /**
     * Creates a list of images for all pieces on the board, and a map with the pieces as keys for their respective image
     * <p>
     * The list is used to clear the board of old piece position and the map is used to calculate the coordinates of the images
     *
     * @return List of piece images
     */
    public List<ImageView> fetchPieceImages() {
        boardMap = model.getCurrentGame().getBoard().getBoardMap();

        pieceImageViewMap.clear();
        for (Map.Entry<Point, Piece> piece : boardMap.entrySet()) {
            if (piece.getValue() == null) {
                break;
            }

            ImageView pieceImage = createPieceImage(piece.getValue());

            pieceImage.setFitWidth(squareDimension - 10);
            pieceImage.setFitHeight(squareDimension - 10);

            pieceImage.setX(piece.getKey().x * squareDimension + 5);
            pieceImage.setY(piece.getKey().y * squareDimension + 5);

            pieceImages.add(pieceImage);
            pieceImageViewMap.put(piece.getValue(), pieceImage);
        }
        return pieceImages;
    }

    ImageView createPieceImage(Piece piece) {
        String imageURL = String.format("/chesspieces/%s_%s.png", piece.getColor().toString(), piece.getPieceType().toString());

        ImageView pieceImage = new ImageView();
        try {
            pieceImage.setImage(new Image(getClass().getResourceAsStream(imageURL)));
        } catch (NullPointerException e) {
            pieceImage.setImage(new Image(getClass().getResourceAsStream("/missing_texture.png")));
        }

        return pieceImage;
    }

    public void fetchDeadPieceImages() {
        List<Piece> deadPieces = model.getCurrentGame().getDeadPieces();
        whiteImageViews.clear();
        blackImageViews.clear();

        for (Piece piece : deadPieces) {
            if (piece == null) {
                break;
            }

            ImageView pieceImage = createPieceImage(piece);

            pieceImage.setFitWidth(squareDimension - 25);
            pieceImage.setFitHeight(squareDimension - 25);

            if (piece.getColor() == WHITE) {
                whiteImageViews.add(pieceImage);
            } else if (piece.getColor() == BLACK) {
                blackImageViews.add(pieceImage);
            }

        }
    }


    /**
     * Uses the map created in fetchPieceImages to calculate the coordinates that the images are supposed to have
     */
    public void updateImageCoordinates() {
        boardMap = model.getCurrentGame().getBoard().getBoardMap();
        for (Map.Entry<Point, Piece> entry : boardMap.entrySet()) {
            pieceImageViewMap.get(entry.getValue()).setX(entry.getKey().x * squareDimension + 5);
            pieceImageViewMap.get(entry.getValue()).setY(entry.getKey().y * squareDimension + 5);
        }
    }

    /**
     * Finds and returns a list of images for the squares a piece is allowed to move to
     *
     * @return returns a list of images to indicate current legal moves
     */
    List<ImageView> fetchLegalMoveImages() {
        List<ImageView> imageViews = new ArrayList<>();
        for (Point point : model.getGame().getLegalPoints()) {
            ImageView imageView = new ImageView();

            if (boardMap.get(point) != null) {
                imageView.setImage(new Image(getClass().getResourceAsStream("/legalMoveBox.png")));
            } else {
                imageView.setImage(new Image(getClass().getResourceAsStream("/legalMove.png")));
            }

            imageView.setFitWidth(squareDimension);
            imageView.setFitHeight(squareDimension);

            imageView.setX(point.x * squareDimension);
            imageView.setY(point.y * squareDimension);

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

    //Game
    public void initTest() {
        boardMap = model.getCurrentGame().getBoard().getBoardMap();
    }
}
