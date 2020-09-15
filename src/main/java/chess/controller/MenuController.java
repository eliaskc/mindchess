package chess.controller;

import chess.model.Chess;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {
    Chess model = Chess.getInstance();
    public FXMLLoader chessLoader;
    Parent parent;

    @FXML private Button btnStart;
    @FXML private Button btnExit;
    @FXML private TextField player1NameField;
    @FXML private TextField player2NameField;
    @FXML private Slider timerSlider;

    @FXML
    void goToBoard (ActionEvent event) throws IOException {
        model.getPlayer1().setName(player1NameField.getText());
        model.getPlayer2().setName(player2NameField.getText());
        model.getPlayer1().setTimer((int)timerSlider.getValue());
        model.getPlayer2().setTimer((int)timerSlider.getValue());

        chessLoader = new FXMLLoader(getClass().getClassLoader().getResource("board.fxml"));
        try {
            parent = chessLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addObserver(chessLoader.getController());

        Scene scene = new Scene(parent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    @FXML
    void Exit (ActionEvent event) {
        System.exit(0);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        chessLoader = new FXMLLoader(getClass().getClassLoader().getResource("board.fxml"));
        try {
            parent = chessLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addObserver(chessLoader.getController());
    }
}
