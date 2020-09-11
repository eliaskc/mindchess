package chess.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class ChessController {

    @FXML Button btnBack;

    @FXML
    void goToMenu (ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("menu.fxml"));
        Scene scene = new Scene(parent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
}
