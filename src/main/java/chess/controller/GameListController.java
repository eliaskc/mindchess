package chess.controller;

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
    private Label lblGameList;

    public GameListController(String player1Name, String player2Name, String gameStatus) {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gameListView.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        //this.lblGameList.setText(String.format("#%d", player1Name + " vs " + player2Name + ": Matchstatus-" + gameStatus));
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        this.lblGameList.setText(player1Name + " vs " + player2Name + ": Matchstatus-" + gameStatus);
    }
}
