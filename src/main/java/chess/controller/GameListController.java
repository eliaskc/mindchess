package chess.controller;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

import java.awt.*;

public class GameListController extends AnchorPane {
    private String player1Name;
    private String player2Name;
    private String gameStatus;

    @FXML
    private Label gameLabel;

    public GameListController(String player1Name, String player2Name, String gameStatus) {
        this.player1Name = player1Name;
        this.player2Name = player2Name;
        this.gameStatus = gameStatus;
    }


}
