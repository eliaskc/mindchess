package chess.controller;

import chess.model.ChessColor;
import chess.model.ChessFacade;
import chess.model.Piece;
import chess.model.PieceType;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static chess.model.ChessColor.BLACK;
import static chess.model.ChessColor.WHITE;

/**
 * Is responsible for fetching the images from files and matching them with the right pieces
 */
public class ImageHandler {
    List<ImageView> blackImageViews = new ArrayList<>();
    List<ImageView> whiteImageViews = new ArrayList<>();
    private final List<ImageView> pieceImages = new ArrayList<>();
    private final Map<Piece, ImageView> pieceImageViewMap = new HashMap<>();
    private ChessFacade model;
    private Map<Point, Piece> boardMap;
    private double squareDimension;
    private boolean minecraftPieceStyle = false;

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

    public boolean isMinecraftPieceStyle() {
        return minecraftPieceStyle;
    }

    public void setMinecraftPieceStyle(boolean minecraftPieceStyle) {
        this.minecraftPieceStyle = minecraftPieceStyle;
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
        for (Map.Entry<Point, Piece> entry : boardMap.entrySet()) {
            if (entry.getValue() == null) {
                break;
            }

            ImageView pieceImageView = new ImageView();
            pieceImageView.setPreserveRatio(true);
            pieceImageView.setImage(createPieceImage(entry.getValue().getPieceType(), entry.getValue().getColor()));

            pieceImageView.setFitWidth(squareDimension - 10);
            pieceImageView.setFitHeight(squareDimension - 10);

            pieceImageView.setX(entry.getKey().x * squareDimension + 5);
            pieceImageView.setY(entry.getKey().y * squareDimension + 5);

            pieceImages.add(pieceImageView);
            pieceImageViewMap.put(entry.getValue(), pieceImageView);
        }
        return pieceImages;
    }

    Image createPieceImage(PieceType pieceType, ChessColor chessColor) {
        String imageURL;

        if (minecraftPieceStyle) {
            imageURL = String.format("/minecraftChesspieces/%s_minecraft_%s.png", chessColor.toString().toLowerCase(), pieceType.toString().toLowerCase());
        } else {
            imageURL = String.format("/chessPieces/%s_%s.png", chessColor.toString().toLowerCase(), pieceType.toString().toLowerCase());
        }

        Image pieceImage;
        try {
            pieceImage = new Image(getClass().getResourceAsStream(imageURL));
        } catch (NullPointerException e) {
            pieceImage = new Image(getClass().getResourceAsStream("/guiFiles/missingTexture.png"));
        }

        return pieceImage;
    }

    public void fetchDeadPieceImages() {
        List<Piece> deadPieces = model.getCurrentGame().getBoard().getDeadPieces();
        whiteImageViews.clear();
        blackImageViews.clear();

        for (Piece piece : deadPieces) {
            if (piece == null) {
                break;
            }

            ImageView pieceImageView = new ImageView();
            pieceImageView.setPreserveRatio(true);
            pieceImageView.setImage(createPieceImage(piece.getPieceType(), piece.getColor()));

            pieceImageView.setFitWidth(squareDimension - 25);
            pieceImageView.setFitHeight(squareDimension - 25);

            if (piece.getColor() == WHITE) {
                whiteImageViews.add(pieceImageView);
            } else if (piece.getColor() == BLACK) {
                blackImageViews.add(pieceImageView);
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
        for (Point point : model.getCurrentGame().getLegalPoints()) {
            ImageView imageView = new ImageView();

            if (boardMap.get(point) != null) {
                if(minecraftPieceStyle) imageView.setImage(new Image(getClass().getResourceAsStream("/guiFiles/minecraftLegalMoveBox.png")));
                else imageView.setImage(new Image(getClass().getResourceAsStream("/guiFiles/legalMoveBox.png")));
            } else {
                if(minecraftPieceStyle) imageView.setImage(new Image(getClass().getResourceAsStream("/guiFiles/minecraftLegalMove.png")));
                else imageView.setImage(new Image(getClass().getResourceAsStream("/guiFiles/legalMove.png")));
            }

            imageView.setFitWidth(squareDimension);
            imageView.setFitHeight(squareDimension);

            imageView.setX(point.x * squareDimension);
            imageView.setY(point.y * squareDimension);

            imageViews.add(imageView);
        }
        return imageViews;
    }

    double distanceFromMarkedPiece(ImageView imageView, int x, int y){
        double yDelta = imageView.getY() - y*squareDimension;
        double xDelta = imageView.getX() - x*squareDimension;
        return Math.hypot(yDelta, xDelta);
    }

    public double getSquareDimension() {
        return squareDimension;
    }

    public void setSquareDimension(double squareDimension) {
        this.squareDimension = squareDimension;
    }

    //Game
    public void init() {
        boardMap = model.getCurrentGame().getBoard().getBoardMap();
    }

    public Image getChessboardImage() {
        String imageURL;

        if (minecraftPieceStyle) {
            imageURL = "/guiFiles/minecraftChessboard.png";
        } else {
            imageURL = "/guiFiles/chessboardBeigeAndBrown.png";
        }

        Image pieceImage;
        try {
            pieceImage = new Image(getClass().getResourceAsStream(imageURL));
        } catch (NullPointerException e) {
            pieceImage = new Image(getClass().getResourceAsStream("/guiFiles/missingTexture.png"));
        }

        return pieceImage;
    }

    Image createKingInCheckImage() {
        try {
            return new Image(getClass().getResourceAsStream("/guiFiles/kingInCheck.png"));
        } catch (NullPointerException e) {
            return new Image(getClass().getResourceAsStream("/guiFiles/missingTexture.png"));
        }
    }
}
