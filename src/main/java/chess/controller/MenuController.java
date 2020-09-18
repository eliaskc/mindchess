package chess.controller;

import chess.model.Chess;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * MenuController handles the start page and movement from it.
 */
public class MenuController implements Initializable {
    Chess model = Chess.getInstance();
    public FXMLLoader chessLoader;
    Parent parent;

    @FXML private ImageView btnStart;
    @FXML private ImageView btnExit;
    @FXML private TextField player1NameField;
    @FXML private TextField player2NameField;
    @FXML private Slider timerSlider;
    @FXML private Label timeLabel;

    /**
     * Gets the inputs from the start page and switches to the board scene, and brings the inputs with it
     *
     * Happens when you click the start button
     *
     * @param event Clicked the button
     * @throws IOException
     */
    @FXML
    void goToBoard (MouseEvent event) throws IOException {
        model.getPlayer1().setName(player1NameField.getText());
        model.getPlayer2().setName(player2NameField.getText());
        model.getPlayer1().setTimer((int)timerSlider.getValue());
        model.getPlayer2().setTimer((int)timerSlider.getValue());

        chessLoader = new FXMLLoader(getClass().getClassLoader().getResource("boardView.fxml"));
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

    /**
     * Exits the application when called
     *
     * @param event Pressed the button
     */
    @FXML
    void Exit (MouseEvent event) {
        System.exit(0);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        chessLoader = new FXMLLoader(getClass().getClassLoader().getResource("boardView.fxml"));
        try {
            parent = chessLoader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        model.addObserver(chessLoader.getController());

        //Adds a listener to timeSlider, updates label that displays time dynamically
        timerSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observableValue, Number number, Number t1) {
                timeLabel.textProperty().setValue(t1.intValue() + " m.");
            }
        });
    }
}
