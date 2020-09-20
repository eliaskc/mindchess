package chess;

//import chess.view.ChessView;
//import chess.view.MenuView;
import chess.controller.ChessController;
import chess.controller.MenuController;
import chess.model.ChessFacade;
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
		FXMLLoader menuLoader = new FXMLLoader(getClass().getClassLoader().getResource("menuView.fxml"));
		FXMLLoader chessLoader = new FXMLLoader(getClass().getClassLoader().getResource("boardView.fxml"));

		Parent menuParent = menuLoader.load();
		Parent chessParent = chessLoader.load();

		MenuController menuController = menuLoader.getController();
		ChessController chessController = chessLoader.getController();

		menuController.setChessParent(menuParent);
		chessController.setMenuParent(chessParent);

		Scene scene = new Scene(menuParent);
		stage.setScene(scene);
		stage.show();

		ChessFacade model = ChessFacade.getInstance();
		model.addObserver(chessController);
	}

	public static void main(String[] args) {
		Application.launch(args);
	}


}
