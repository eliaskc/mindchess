package mindchess.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import mindchess.model.Ply;
import mindchess.model.Square;
import mindchess.model.pieces.IPiece;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Responsible for fetching information about a ply (half-move) and displaying it
 * in the plyView.fxml
 */
class PlyController extends AnchorPane {
    private final Ply ply;
    private final ImageHandlerUtil imageHandlerUtil;

    @FXML
    private Label labelPlyNumber;
    @FXML
    private Label labelMovedFrom;
    @FXML
    private Label labelMovedTo;
    @FXML
    private Label labelPlayer;
    @FXML
    private ImageView imagePiece;

    PlyController(Ply ply, int plyNum, ImageHandlerUtil imageHandlerUtil) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/plyView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.ply = ply;
        this.imageHandlerUtil = imageHandlerUtil;
        this.imagePiece.setImage(imageHandlerUtil.createPieceImage(ply.getMovedPiece().getPieceType(), ply.getMovedPiece().getColor()));
        this.labelPlyNumber.setText(String.format("#%d", plyNum));
        this.labelMovedFrom.setText(String.format("%s%s", translateXCoordinate(ply.getMovedFrom().getX()), translateYCoordinate(ply.getMovedFrom().getY())));
        this.labelMovedTo.setText(String.format("%s%s", translateXCoordinate(ply.getMovedTo().getX()), translateYCoordinate(ply.getMovedTo().getY())));
        this.labelPlayer.setText(String.format("%s", ply.getPlayerName()));
    }

    /**
     * Generates a list of ImageViews using a snapshot of the board after a move
     * @param performMove Dictates whether we want to perform the ply associated with this object or not
     * <p> If false: the generated board will look like it did before the move was made
     * <p> If true: the generated board will look like it did after the move
     * @return a list of all active pieces for the current board snapshot
     */
    List<ImageView> generateBoardImages(boolean performMove) {
        List<ImageView> imageViewList = new ArrayList<>();

        for (Map.Entry<Square, IPiece> entry : ply.getBoardSnapshot().entrySet()) {
            ImageView imageView = imageHandlerUtil.createPieceImageView(entry.getKey(), entry.getValue().getPieceType(), entry.getValue().getColor(), 40);

            imageViewList.add(imageView);

            if (entry.getValue().equals(ply.getMovedPiece())) {

                if (performMove) {
                    imageHandlerUtil.addTranslateTransition(imageView, ply.getMovedFrom(), ply.getMovedTo(), 40, 400);
                } else {
                    imageView.setX(ply.getMovedFrom().getX() * 40);
                    imageView.setY(ply.getMovedFrom().getY() * 40);
                }

                if (Objects.nonNull(ply.getTakenPiece())) {
                    ImageView attackedImageView = imageHandlerUtil.createPieceImageView(ply.getMovedTo(), ply.getTakenPiece().getPieceType(), ply.getTakenPiece().getColor(), 40);
                    imageHandlerUtil.addScaleTransition(attackedImageView, 400, false);
                    imageViewList.add(attackedImageView);
                }
            }
        }

        return imageViewList;
    }

    /**
     * Translates the x-position of a piece in the board map to a chessboard coordinate
     * @param x X-position
     * @return the translated x-coordinate
     */
    private String translateXCoordinate(int x) {
        String[] translation = {"a", "b", "c", "d", "e", "f", "g", "h"};
        return translation[x];
    }

    /**
     * Translates the y-position of a piece in the board map to a chessboard coordinate
     * @param y Y-position
     * @return the translated y-coordinate
     */
    private String translateYCoordinate(int y) {
        String[] translation = {"8", "7", "6", "5", "4", "3", "2", "1"};
        return translation[y];
    }
}
