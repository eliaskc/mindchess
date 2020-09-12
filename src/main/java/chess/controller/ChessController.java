package chess.controller;

import chess.model.Chess;
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
import javafx.scene.input.*;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChessController implements Initializable {

    Chess model = Chess.getInstance();

    @FXML Button btnBack;
    @FXML private Label player1Name;
    @FXML private Label player2Name;
    @FXML private Label player1Timer;
    @FXML private Label player2Timer;


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
        player1Name.setText(model.getPlayer1().getName());
        player2Name.setText(model.getPlayer2().getName());
    }

    @FXML
    public void findSquare(MouseEvent event){
        model.findSquare(event.getSceneX(),event.getSceneY());
    }


}
