package chess;

//import main.java.chess.view.ChessView;
//import main.java.chess.view.MenuView;
//import main.java.chess.model.Chess;
//import main.java.chess.controller.ChessController;
//import main.java.chess.controller.MenuController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class ChessApplication extends Application {
	@Override
	public void start(Stage stage) throws Exception {
		System.out.println("Hellooo");
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("menu.fxml"));
		Scene scene = new Scene(root);

		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
