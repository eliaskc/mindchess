package chess;

import chess.controller.ChessController;
import chess.controller.MenuController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public final class ChessApplication extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader menuLoader = new FXMLLoader(getClass().getClassLoader().getResource("menuView.fxml"));
		FXMLLoader chessLoader = new FXMLLoader(getClass().getClassLoader().getResource("boardView.fxml"));

		Parent menuParent = menuLoader.load();
		Parent chessParent = chessLoader.load();

		MenuController menuController = menuLoader.getController();
		ChessController chessController = chessLoader.getController();

		menuController.createChessScene(chessParent);
		chessController.createMenuScene(menuParent);

		menuController.setChessController(chessController);

		//Might need to be reworked since our menu scene is created in our chessController which is kinda weird
		stage.setScene(chessController.getMenuScene());
		stage.show();
	}

	public static void main(String[] args) {
		Application.launch(args);
	}
}
