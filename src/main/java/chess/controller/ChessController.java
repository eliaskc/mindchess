package chess.controller;

import chess.Observer;
import chess.model.Chess;
import chess.model.pieces.Piece;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChessController implements Initializable, Observer {

    Chess model = Chess.getInstance();

    List<ImageView> pieceImages = model.getBoard().getPieceImages();

    @FXML Button btnBack;
    @FXML private Label player1Name;
    @FXML private Label player2Name;
    @FXML private Label player1Timer;
    @FXML private Label player2Timer;
    @FXML private ImageView chessBoardImage;
    @FXML private AnchorPane chessBoardContainer;

    @FXML
    void goToMenu (ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("menu.fxml"));
        Scene scene = new Scene(parent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        System.out.println("Initialized ChessController");
        player1Name.setText(model.getPlayer1().getName());
        player2Name.setText(model.getPlayer2().getName());
        drawPieces();
    }

    @FXML
    public void findSquare(MouseEvent event){
        model.findSquare(event.getSceneX(),event.getSceneY());
    }

    public ImageView getChessBoardImage() {
        return chessBoardImage;
    }

    public void drawPieces() {
        for (ImageView pieceImage : pieceImages) {
            chessBoardContainer.getChildren().remove(pieceImage);
        }

        for (ImageView pieceImage : pieceImages) {
            chessBoardContainer.getChildren().add(pieceImage);
            chessBoardContainer.getChildren().get(chessBoardContainer.getChildren().indexOf(pieceImage)).setMouseTransparent(true);
        }
    }

    @Override
    public void onAction() {
        drawPieces();
    }
}
