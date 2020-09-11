package chess.controller;

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

    @FXML private Button btnStart;
    @FXML private Button btnExit;



    @FXML
    void goToBoard (ActionEvent event) throws IOException {
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
