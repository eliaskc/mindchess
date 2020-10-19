package chess.controller;

import chess.model.ChessColor;
import chess.model.ChessFacade;
import chess.model.PieceType;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Is responsible for fetching the images from files and matching them with the right pieces
 */
public class ImageHandler {
    private ChessFacade model;
    private double squareDimension;
    private boolean minecraftPieceStyle = false;

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
    public ImageView fetchPieceImageView(Point point, PieceType pieceType, ChessColor chessColor, int dimensions) {
        ImageView pieceImageView = new ImageView();

        pieceImageView.setPreserveRatio(true);
        pieceImageView.setImage(createPieceImage(pieceType, chessColor));

        pieceImageView.setFitWidth(dimensions);
        pieceImageView.setFitHeight(dimensions);

        pieceImageView.setX(point.x * dimensions);
        pieceImageView.setY(point.y * dimensions);

        return pieceImageView;
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


    void addTranslateTransition(ImageView imageView, Point pointFrom, Point pointTo, int dimensions){
        imageView.setX(0);
        imageView.setY(0);

        TranslateTransition tt = new TranslateTransition(Duration.millis(500), imageView);
        tt.setFromX(pointFrom.x*dimensions);
        tt.setFromY(pointFrom.y*dimensions);
        tt.setToX(pointTo.x*dimensions);
        tt.setToY(pointTo.y*dimensions);
        tt.setCycleCount(1);
        tt.play();
    }

    void addScaleTransition(ImageView imageView, double duration, boolean grow){
        ScaleTransition st = new ScaleTransition(Duration.millis(duration), imageView);
        if (grow){
            st.setFromX(0);
            st.setFromY(0);
            st.setToX(1.0);
            st.setToY(1.0);
        } else {
            st.setFromX(1.0);
            st.setFromY(1.0);
            st.setToX(0);
            st.setToY(0);
        }
        st.setCycleCount(1);
        st.play();
    }

    public List<ImageView> fetchDeadPieceImages(ChessColor chessColor) {
        List<ImageView> imageViews = new ArrayList<>();
        for (PieceType pieceType : model.getCurrentDeadPiecesByColor(chessColor)) {

           ImageView imageView = fetchPieceImageView(new Point(0,0), pieceType, chessColor, (int) squareDimension);

           imageView.setFitWidth(squareDimension - 25);
           imageView.setFitHeight(squareDimension - 25);

           imageViews.add(imageView);
        }

        return imageViews;
    }

    /**
     * Finds and returns a list of images for the squares a piece is allowed to move to
     *
     * @return returns a list of images to indicate current legal moves
     */
    List<ImageView> fetchLegalMoveImages() {
        List<ImageView> imageViews = new ArrayList<>();
        for (Point point : model.getCurrentLegalPoints()) {
            ImageView imageView = new ImageView();

            if (model.isPointOccupied(point)) {
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
