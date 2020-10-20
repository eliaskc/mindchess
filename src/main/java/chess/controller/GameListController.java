package chess.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;

public class GameListController extends AnchorPane {

    public GameListController(String player1Name, String player2Name, String gameStatus) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gameListView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        //lblGameLabel.setText(player1Name + " vs " + player2Name + ": Matchstatus-" + gameStatus);
    }
}
