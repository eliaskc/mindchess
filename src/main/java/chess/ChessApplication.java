package chess;

//import chess.view.ChessView;
//import chess.view.MenuView;
import chess.controller.ChessController;
import chess.controller.MenuController;
import chess.model.Chess;
//import chess.controller.ChessController;
//import chess.controller.MenuController;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class ChessApplication extends Application {

	@Override
	public void start(Stage stage) throws Exception {
		FXMLLoader menuLoader = new FXMLLoader(getClass().getClassLoader().getResource("menu.fxml"));
		Parent root = menuLoader.load();
		Scene scene = new Scene(root);
		stage.setScene(scene);
		stage.show();

		MenuController menuController = menuLoader.getController();

		Chess model = Chess.getInstance();
		ChessController chessController = menuController.chessLoader.getController();

		model.getBoard().setChessBoardImage(chessController.getChessBoardImage());
		model.addObserver(chessController);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}


}
