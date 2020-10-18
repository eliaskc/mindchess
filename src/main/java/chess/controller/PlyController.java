package chess.controller;

import chess.model.Piece;
import chess.model.Ply;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class PlyController extends AnchorPane {
    private Ply ply;
    private ImageHandler imageHandler;

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

    public PlyController(Ply ply, int plyNum, ImageHandler imageHandler){
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/plyView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.ply = ply;
        this.imageHandler = imageHandler;
        this.imagePiece.setImage(imageHandler.createPieceImage(ply.getMovedPiece().getPieceType(), ply.getMovedPiece().getColor()));
        this.labelPlyNumber.setText(String.format("#%d", plyNum));
        this.labelMovedFrom.setText(String.format("x%d y%d", ply.getMovedFrom().x, ply.getMovedFrom().y));
        this.labelMovedTo.setText(String.format("x%d y%d", ply.getMovedTo().x, ply.getMovedTo().y));
        this.labelPlayer.setText(String.format("%s", ply.getPlayerName()));
    }

    public List<ImageView> generateBoardImages(boolean performMove){
        List<ImageView> imageViewList = new ArrayList<>();

        for (Map.Entry<Point, Piece> entry : ply.getBoardSnapshot().entrySet()){
            ImageView imageView = new ImageView();
            imageView.setImage(imageHandler.createPieceImage(entry.getValue().getPieceType(), entry.getValue().getColor()));
            imageView.setFitWidth(40);
            imageView.setFitHeight(40);
            imageView.setX(entry.getKey().x*40);
            imageView.setY(entry.getKey().y*40);
            imageViewList.add(imageView);

            if (entry.getValue().equals(ply.getMovedPiece())) {

                if(performMove){
                    addTranslateTransition(imageView);
                } else {
                    imageView.setX(ply.getMovedFrom().x*40);
                    imageView.setY(ply.getMovedFrom().y*40);
                }

                if (ply.getTakenPiece() != null) {
                    ImageView attackedImageView = new ImageView();
                    attackedImageView.setImage(imageHandler.createPieceImage(ply.getTakenPiece().getPieceType(), ply.getTakenPiece().getColor()));
                    attackedImageView.setFitWidth(40);
                    attackedImageView.setFitHeight(40);
                    attackedImageView.setX(ply.getMovedTo().x*40);
                    attackedImageView.setY(ply.getMovedTo().y*40);
                    addScaleTransition(attackedImageView);
                    imageViewList.add(attackedImageView);
                }
            }
        }

        return imageViewList;
    }

    private void addTranslateTransition(ImageView imageView){
        imageView.setX(0);
        imageView.setY(0);

        TranslateTransition tt = new TranslateTransition(Duration.millis(750), imageView);
        tt.setFromX(ply.getMovedFrom().x*40);
        tt.setFromY(ply.getMovedFrom().y*40);
        tt.setToX(ply.getMovedTo().x*40);
        tt.setToY(ply.getMovedTo().y*40);
        tt.setCycleCount(1);
        tt.play();
    }

    private void addScaleTransition(ImageView imageView){
        ScaleTransition st = new ScaleTransition(Duration.millis(750), imageView);
        st.setFromX(1.0);
        st.setFromY(1.0);
        st.setToX(0);
        st.setToY(0);
        st.setCycleCount(1);
        st.play();
    }
}
