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

public class MenuController{
    Chess model = Chess.getInstance();

    @FXML private Button btnStart;
    @FXML private Button btnExit;
    @FXML private TextField player1NameField;
    @FXML private TextField player2NameField;


    @FXML
    void goToBoard (ActionEvent event) throws IOException {
        model.getPlayer1().setName(player1NameField.getText());
        model.getPlayer2().setName(player2NameField.getText());
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("board.fxml"));
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
    }


    //FROM TEMPLATE
        //  private final Project project;
        //  private final ProjectView projectView;
        //  public static final int KO = 1;
        //
        //  public static ProjectController create(Project project, ProjectView projectView) {
        //    return new ProjectController(project, projectView);
        //  }
        //
        //  private ProjectController(Project project, ProjectView projectView) {
        //    projectView.getButton().addActionListener(new ProjectButtonPressed());
        //
        //    this.project = project;
        //    this.projectView = projectView;
        //  }
        //
        //  private class ProjectButtonPressed implements ActionListener {
        //    @Override
        //    public void actionPerformed(ActionEvent e) {
        //      project.incrementPresses();
        //      projectView.getPressesLabel().setText(String.valueOf(project.getPresses()));
        //    }
        //  }


}
