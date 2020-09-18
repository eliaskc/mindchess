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

public class ChessController implements Initializable, Observer {

    Chess model = Chess.getInstance();

    List<ImageView> pieceImages = model.getBoard().getPieceImages();

    List<ImageView> legalMoveImages = fetchLegalMoveImages();

    @FXML Button btnBack;
    @FXML private Label player1Name;
    @FXML private Label player2Name;
    @FXML private Label player1Timer;
    @FXML private Label player2Timer;
    @FXML private ImageView chessBoardImage;
    @FXML private AnchorPane chessBoardContainer;

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
        drawPieces();


    }

    /**
     * Sends to the board that it has been clicked on
     * @param event board clicked
     */
    @FXML
    public void handleClick(MouseEvent event){
        model.handleBoardClick(event.getSceneX(),event.getSceneY());
    }

    public ImageView getChessBoardImage() {
        return chessBoardImage;
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

        legalMoveImages = fetchLegalMoveImages();

        for (ImageView imageView : legalMoveImages) {
            chessBoardContainer.getChildren().add(imageView);
            chessBoardContainer.getChildren().get(chessBoardContainer.getChildren().indexOf(imageView)).setMouseTransparent(true);
        }
    }

    /**temp
     *
     * (does) finds and returns a list of images on all squares
     *
     * (should) finds and returns a list of images for the squares a piece is allowd to move to
     *
     * @return returns a list of images on all squares
     */
    private List<ImageView> fetchLegalMoveImages() {
        List<ImageView> imageViews = new ArrayList<>();
        for (Square s : model.getBoard().getMockLegalSquares()) {
            ImageView imageView = new ImageView();
            imageView.setImage(new Image(getClass().getResourceAsStream("/legalMove.png")));
            imageView.setX(s.getCoordinatesX() * 75 + 30);
            imageView.setY(s.getCoordinatesY() * 75 + 30);
            imageView.setFitHeight(15);
            imageView.setFitWidth(15);
            imageViews.add(imageView);
        }
        return imageViews;
    }

    /**
     * draws pieces when called by the observeable, implemented by observer interface
     */
    @Override
    public void onAction() {
        drawPieces();
    }
}
