package chess.controller;

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
import java.util.ResourceBundle;

public class ChessController implements Initializable {

    //Controller should not have model
    Chess model = Chess.getInstance();

    @FXML Button btnBack;
    @FXML private Label player1Name;
    @FXML private Label player2Name;
    @FXML private Label player1Timer;
    @FXML private Label player2Timer;
    @FXML private ImageView chessBoard;
    @FXML private AnchorPane chess_test;


    @FXML
    void goToMenu (ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("menu.fxml"));
        Scene scene = new Scene(parent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    // Implement observer !!!!!!!
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        player1Name.setText(model.getPlayer1().getName());
        player2Name.setText(model.getPlayer2().getName());
    }

    @FXML
    public void findSquare(MouseEvent event){                               //Find the square you clicked on and do something about it.
        model.findSquare(event.getSceneX(),event.getSceneY());
    }

    public ImageView getChessBoard() {
        return chessBoard;
    }

    public void drawPieces(Piece piece) {
        ImageView imageview = new ImageView();
        imageview.setImage(piece.getPieceImage());
        imageview.setX(piece.getPosition().getCoordinatesX());
        imageview.setY(piece.getPosition().getCoordinatesY());
        chess_test.getChildren().add(imageview);
    }
}
