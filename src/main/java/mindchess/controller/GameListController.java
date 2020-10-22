package mindchess.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;

public class GameListController extends AnchorPane {
    @FXML
    private Label player1NameLabel;
    @FXML
    private Label player2NameLabel;
    @FXML
    private Label gameStatusLabel;


    public GameListController(String player1Name, String player2Name, String gameStatus) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gameListView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.player1NameLabel.setText(player1Name);
        this.player2NameLabel.setText(player2Name);
        this.gameStatusLabel.setText(gameStatus);
    }
}
