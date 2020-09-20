package chess.controller;

import chess.Observer;
import chess.model.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.SortedSet;

public class ChessController implements Initializable, Observer {
    Chess model = Chess.getInstance();

    ImageHandler imageHandler = new ImageHandler();
    List<ImageView> pieceImages;

    List<ImageView> legalMoveImages = imageHandler.fetchLegalMoveImages();

    @FXML Button btnBack;
    @FXML private Label player1Name;
    @FXML private Label player2Name;
    @FXML private Label player1Timer;
    @FXML private Label player2Timer;
    @FXML private ImageView chessBoardImage;
    @FXML private AnchorPane chessBoardContainer;

    double squareDimension;
    double chessboardContainerX;
    double chessboardContainerY;

    /**
     * Switches to the menu/startscreen scene
     *
     * @param event Button click
     * @throws IOException
     */
    @FXML
    void goToMenu (ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("menuView.fxml"));
        Scene scene = new Scene(parent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();

        window.setScene(scene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        player1Name.setText(model.getPlayer1().getName());
        player2Name.setText(model.getPlayer2().getName());
        player1Timer.setText(Double.toString(model.getPlayer1().getTimer()));
        player2Timer.setText(Double.toString(model.getPlayer2().getTimer()));

        updateSquareDimensions();
        chessboardContainerX = chessBoardContainer.getLayoutX();
        chessboardContainerY = chessBoardContainer.getLayoutY();

        pieceImages = imageHandler.fetchPieceImages();
        drawPieces();
    }

    public void updateSquareDimensions() {
        squareDimension = chessBoardImage.getFitHeight() / 8;
        imageHandler.setSquareDimension(squareDimension);
    }

    /**
     * Sends to the board that it has been clicked on
     * @param event board clicked
     */
    @FXML
    public void handleClick(MouseEvent event){
        model.handleBoardClick(translateX(event.getSceneX()), translateY(event.getSceneY()));
    }

    /**
     * Translate input x coordinate into a square index x
     * @param x
     * @return
     */
    public int translateX(double x) {
        //hardcoded for now
        for (int i = 0; i < 8; i++) {
            if((i * squareDimension + chessboardContainerX <= x && x <= chessboardContainerX + squareDimension*(i+1))){
                return i;
            }
        }
        throw new IllegalArgumentException("Outside board");
    }

    /**
     * Translate input y coordinate into a square index y
     * @param y
     * @return
     */
    public int translateY(double y) {
        //hardcoded for now
        for (int i = 0; i < 8; i++) {
            if((i * squareDimension + chessboardContainerY <= y && y <= chessboardContainerY + squareDimension*(i+1))){
                return i;
            }
        }
        throw new IllegalArgumentException("Outside board");
    }

    /**
     * Draws all images from the list of pieceImages from the board
     */
    public void drawPieces() {
        for (ImageView pieceImage : pieceImages) {
            chessBoardContainer.getChildren().remove(pieceImage);
        }

        for (ImageView pieceImage : pieceImages) {
            chessBoardContainer.getChildren().add(pieceImage);
            chessBoardContainer.getChildren().get(chessBoardContainer.getChildren().indexOf(pieceImage)).setMouseTransparent(true);
        }

        //TEMP
        for (ImageView imageView : legalMoveImages) {
            chessBoardContainer.getChildren().remove(imageView);
        }

        legalMoveImages = imageHandler.fetchLegalMoveImages();

        for (ImageView imageView : legalMoveImages) {
            chessBoardContainer.getChildren().add(imageView);
            chessBoardContainer.getChildren().get(chessBoardContainer.getChildren().indexOf(imageView)).setMouseTransparent(true);
        }
    }

    /**
     * draws pieces when called by the observable, implemented by observer interface
     */
    @Override
    public void onAction() {
        imageHandler.updateImageCoordinates();
        updateSquareDimensions();
        chessboardContainerX = chessBoardContainer.getLayoutX();
        chessboardContainerY = chessBoardContainer.getLayoutY();
        drawPieces();
    }
}
