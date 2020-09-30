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
    private List<ImageView> pieceImages = new ArrayList<>();
    private Map<Piece, ImageView> pieceImageViewMap = new HashMap<>();
    private ChessFacade model = ChessFacade.getInstance();
    private Map<Point, Piece> boardMap = model.getCurrentGame().getBoard().getBoardMap();

    private double squareDimension;

    public List<ImageView> getPieceImages() {
        return pieceImages;
    }

    /**
     * Creates a list of images for all pieces on the board, and a map with the pieces as keys for their respective image
     *
     * The list is used to clear the board of old piece position and the map is used to calculate the coordinates of the images
     * @return List of piece images
     */
    public List<ImageView> fetchPieceImages() {
        pieceImageViewMap.clear();
        for (Map.Entry<Point,Piece> piece : boardMap.entrySet()) {
            String imageURL = "";
            if (piece.getValue() == null) {
                break;
            }
            switch (piece.getValue().getPieceType()) {
                case ROOK:
                    if (piece.getValue().getColor().equals(BLACK)) {
                        imageURL = "/chesspieces/black_rook.png";
                    } else if(piece.getValue().getColor().equals(WHITE)) {
                        imageURL = "/chesspieces/white_rook.png";
                    }
                    break;
                case BISHOP:
                    if (piece.getValue().getColor().equals(BLACK)) {
                        imageURL = "/chesspieces/black_bishop.png";
                    } else if(piece.getValue().getColor().equals(WHITE)) {
                        imageURL = "/chesspieces/white_bishop.png";
                    }
                    break;
                case KNIGHT:
                    if (piece.getValue().getColor().equals(BLACK)) {
                        imageURL = "/chesspieces/black_knight.png";
                    } else if(piece.getValue().getColor().equals(WHITE)) {
                        imageURL = "/chesspieces/white_knight.png";
                    }
                    break;
                case QUEEN:
                    if (piece.getValue().getColor().equals(BLACK)) {
                        imageURL = "/chesspieces/black_queen.png";
                    } else if(piece.getValue().getColor().equals(WHITE)) {
                        imageURL = "/chesspieces/white_queen.png";
                    }
                    break;
                case KING:
                    if (piece.getValue().getColor().equals(BLACK)) {
                        imageURL = "/chesspieces/black_king.png";
                    } else if(piece.getValue().getColor().equals(WHITE)) {
                        imageURL = "/chesspieces/white_king.png";
                    }
                    break;
                case PAWN:
                    if (piece.getValue().getColor().equals(BLACK)) {
                        imageURL = "/chesspieces/black_pawn.png";
                    } else if(piece.getValue().getColor().equals(WHITE)) {
                        imageURL = "/chesspieces/white_pawn.png";
                    }
                    break;
            }
            ImageView pieceImage = new ImageView();
            pieceImage.setImage(new Image(getClass().getResourceAsStream(imageURL)));
            pieceImage.setFitWidth(squareDimension-10);
            pieceImage.setFitHeight(squareDimension-10);

            pieceImage.setX(piece.getKey().x * squareDimension + 5);
            pieceImage.setY(piece.getKey().y * squareDimension + 5);

            pieceImages.add(pieceImage);
            pieceImageViewMap.put(piece.getValue(), pieceImage);
        }
        return pieceImages;
    }

    /**
     * Uses the map created in fetchPieceImages to calculate the coordinates that the images are supposed to have
     */
    public void updateImageCoordinates() {
        for(Map.Entry<Point, Piece> entry : boardMap.entrySet()) {
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

            if(boardMap.get(point) != null){
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
