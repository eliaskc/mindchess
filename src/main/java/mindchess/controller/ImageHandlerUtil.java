package mindchess.controller;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;
import mindchess.model.enums.ChessColor;
import mindchess.model.ChessFacade;
import mindchess.model.enums.PieceType;
import mindchess.model.Square;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for fetching the images from files and matching them with the correct objects
 */
public class ImageHandlerUtil {
    private ChessFacade model;
    private double squareDimension;
    private boolean minecraftPieceStyle = false;

    /**
     * Calculates the distance from an ImageView to the last clicked x and y coordinate
     * @param imageView the image to check for
     * @param x Last clicked x coordinate
     * @param y Last clicked y coordinate
     * @return the distance
     */
    double distanceFromMarkedPiece(ImageView imageView, int x, int y) {
        double yDelta = imageView.getY() - y * squareDimension;
        double xDelta = imageView.getX() - x * squareDimension;
        return Math.hypot(yDelta, xDelta);
    }

    //-------------------------------------------------------------------------------------
    //Image
    Image createKingInCheckImage() {
        try {
            return new Image(getClass().getResourceAsStream("/guiFiles/kingInCheck.png"));
        } catch (NullPointerException e) {
            return new Image(getClass().getResourceAsStream("/guiFiles/missingTexture.png"));
        }
    }

    ScaleTransition addScaleTransition(ImageView imageView, double duration, boolean grow) {
        ScaleTransition st = new ScaleTransition(Duration.millis(duration), imageView);
        if (grow) {
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
        return st;
    }

    /**
     * Creates and returns a list of ImageViews of the dead/taken pieces of a specified color
     * @param chessColor The color of the dead pieces that are to be returned
     * @return a list of images of all dead pieces of the specified color
     */
    public List<ImageView> fetchDeadPieceImages(ChessColor chessColor) {
        List<ImageView> imageViews = new ArrayList<>();
        for (PieceType pieceType : model.getCurrentDeadPiecesByColor(chessColor)) {
            ImageView imageView = createPieceImageView(new Square(0, 0), pieceType, chessColor, (int) squareDimension);

            imageView.setFitWidth(squareDimension - 25);
            imageView.setFitHeight(squareDimension - 25);

            imageViews.add(imageView);
        }

        return imageViews;
    }

    /**
     * Creates and returns an ImageView of a chess piece
     * @param square Square at which the image is to be placed
     * @param pieceType the type of the piece to create the image for
     * @param chessColor the color of the piece
     * @param dimensions Width and height of the image
     * @return the ImageView of the piece
     */
    public ImageView createPieceImageView(Square square, PieceType pieceType, ChessColor chessColor, int dimensions) {
        ImageView pieceImageView = new ImageView();

        pieceImageView.setPreserveRatio(true);
        pieceImageView.setImage(createPieceImage(pieceType, chessColor));

        pieceImageView.setFitWidth(dimensions);
        pieceImageView.setFitHeight(dimensions);

        pieceImageView.setX(square.getX() * dimensions);
        pieceImageView.setY(square.getY() * dimensions);

        return pieceImageView;
    }

    Image createPieceImage(PieceType pieceType, ChessColor pieceColor) {
        java.lang.String imageURL;

        if (minecraftPieceStyle) {
            imageURL = java.lang.String.format("/minecraftChesspieces/%s_minecraft_%s.png", pieceColor.toString().toLowerCase(), pieceType.toString().toLowerCase());
        } else {
            imageURL = java.lang.String.format("/chessPieces/%s_%s.png", pieceColor.toString().toLowerCase(), pieceType.toString().toLowerCase());
        }

        Image pieceImage;
        try {
            pieceImage = new Image(getClass().getResourceAsStream(imageURL));
        } catch (NullPointerException e) {
            pieceImage = new Image(getClass().getResourceAsStream("/guiFiles/missingTexture.png"));
        }

        return pieceImage;
    }

    void addTranslateTransition(ImageView imageView, Square squareFrom, Square squareTo, int dimensions, int duration) {
        imageView.setX(0);
        imageView.setY(0);

        TranslateTransition tt = new TranslateTransition(Duration.millis(duration), imageView);
        tt.setFromX(squareFrom.getX() * dimensions);
        tt.setFromY(squareFrom.getY() * dimensions);
        tt.setToX(squareTo.getX() * dimensions);
        tt.setToY(squareTo.getY() * dimensions);
        tt.setCycleCount(1);
        tt.play();
    }

    /**
     * Finds and returns a list of images for the squares a piece is allowed to move to
     *
     * @return returns a list of images to indicate current legal moves
     */
    List<ImageView> fetchLegalMoveImages() {
        List<ImageView> imageViews = new ArrayList<>();
        for (Square square : model.getCurrentLegalSquares()) {
            ImageView imageView = new ImageView();

            if (model.isSquareOccupied(square)) {
                if (minecraftPieceStyle)
                    imageView.setImage(new Image(getClass().getResourceAsStream("/guiFiles/minecraftLegalMoveBox.png")));
                else imageView.setImage(new Image(getClass().getResourceAsStream("/guiFiles/legalMoveBox.png")));
            } else {
                if (minecraftPieceStyle)
                    imageView.setImage(new Image(getClass().getResourceAsStream("/guiFiles/minecraftLegalMove.png")));
                else imageView.setImage(new Image(getClass().getResourceAsStream("/guiFiles/legalMove.png")));
            }

            imageView.setFitWidth(squareDimension);
            imageView.setFitHeight(squareDimension);

            imageView.setX(square.getX() * squareDimension);
            imageView.setY(square.getY() * squareDimension);

            imageViews.add(imageView);
        }
        return imageViews;
    }

    //-------------------------------------------------------------------------------------
    //Getters
    public Image getChessboardImage() {
        java.lang.String imageURL;

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

    //-------------------------------------------------------------------------------------
    //Setters
    public void setSquareDimension(double squareDimension) {
        this.squareDimension = squareDimension;
    }

    public boolean isMinecraftPieceStyle() {
        return minecraftPieceStyle;
    }

    public void setMinecraftPieceStyle(boolean minecraftPieceStyle) {
        this.minecraftPieceStyle = minecraftPieceStyle;
    }

    public void setModel(ChessFacade model) {
        this.model = model;
    }
}
