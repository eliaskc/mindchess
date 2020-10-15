package chess.controller;

import chess.model.Piece;
import chess.model.Ply;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
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
        this.labelPlyNumber.setText(String.format("#%d", plyNum));
        this.labelMovedFrom.setText(String.format("x%d y%d", ply.getMovedFrom().x, ply.getMovedFrom().y));
        this.labelMovedTo.setText(String.format("x%d y%d", ply.getMovedTo().x, ply.getMovedTo().y));
        //TODO add playerName string to Ply and use instead of Color
        this.labelPlayer.setText(String.format("%s", ply.getMovedPiece().getColor()));
    }

    public List<ImageView> generateBoardImages(boolean animatePieces){
        List<ImageView> imageViewList = new ArrayList<>();

        if(animatePieces){
            for (Map.Entry<Point, Piece> entry : ply.getBoardSnapshot().entrySet()){
                //Only draw the pieces that weren't moved/attacked
                if (entry.getKey().equals(ply.getMovedTo()) || entry.getKey().equals(ply.getMovedFrom())) {
                    imageViewList.addAll(animatePieceMove(ply));
                } else {
                    ImageView imageView = new ImageView();
                    imageView.setImage(imageHandler.createPieceImage(entry.getValue().getPieceType(), entry.getValue().getColor()));
                    imageView.setFitWidth(40);
                    imageView.setFitHeight(40);
                    imageView.setX(entry.getKey().x*40);
                    imageView.setY(entry.getKey().y*40);
                    imageViewList.add(imageView);
                }
            }
        } else {
            for (Map.Entry<Point, Piece> entry : ply.getBoardSnapshot().entrySet()){
                ImageView imageView = new ImageView();
                imageView.setImage(imageHandler.createPieceImage(entry.getValue().getPieceType(), entry.getValue().getColor()));
                imageView.setFitWidth(40);
                imageView.setFitHeight(40);
                imageView.setX(entry.getKey().x*40);
                imageView.setY(entry.getKey().y*40);
                imageViewList.add(imageView);
            }
        }

        return imageViewList;
    }

    private List<ImageView> animatePieceMove(Ply ply){
        List<ImageView> imageViewList = new ArrayList<>();

        ImageView movedPiece = new ImageView();
        movedPiece.setImage(imageHandler.createPieceImage(ply.getMovedPiece().getPieceType(), ply.getMovedPiece().getColor()));
        movedPiece.setFitWidth(40);
        movedPiece.setFitHeight(40);
        movedPiece.setX(ply.getMovedFrom().x*40);
        movedPiece.setY(ply.getMovedFrom().y*40);

        ScaleTransition st = new ScaleTransition(Duration.millis(500), movedPiece);
        st.setFromX(1.0);
        st.setFromY(1.0);
        st.setToX(0);
        st.setToY(0);
        st.setCycleCount(1);

        st.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                movedPiece.setX(ply.getMovedTo().x*40);
                movedPiece.setY(ply.getMovedTo().y*40);
            }
        });

        ScaleTransition st2 = new ScaleTransition(Duration.millis(500), movedPiece);
        st2.setFromX(0);
        st2.setFromY(0);
        st2.setToX(1.0);
        st2.setToY(1.0);
        st2.setCycleCount(1);


        ImageView attackedPiece = new ImageView();
        if(ply.getBoardSnapshot().containsKey(ply.getMovedTo())){
            attackedPiece.setImage(imageHandler.createPieceImage(ply.getBoardSnapshot().get(ply.getMovedTo()).getPieceType(), ply.getBoardSnapshot().get(ply.getMovedTo()).getColor()));
            attackedPiece.setFitWidth(40);
            attackedPiece.setFitHeight(40);
            attackedPiece.setX(ply.getMovedTo().x*40);
            attackedPiece.setY(ply.getMovedTo().y*40);
        }

        ScaleTransition st3 = new ScaleTransition(Duration.millis(500), attackedPiece);
        st3.setFromX(1.0);
        st3.setFromY(1.0);
        st3.setToX(0);
        st3.setToY(0);
        st3.setCycleCount(1);

        ParallelTransition pt = new ParallelTransition(st2, st3);
        SequentialTransition sqt = new SequentialTransition(st, pt);

        sqt.setCycleCount(1);
        sqt.play();

        imageViewList.add(movedPiece);
        imageViewList.add(attackedPiece);
        return imageViewList;
    }

    public void setImagePiece(Image image) {
        this.imagePiece.setImage(image);
    }
}
