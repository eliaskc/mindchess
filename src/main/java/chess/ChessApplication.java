package chess;

//import chess.view.ChessView;
//import chess.view.MenuView;
import chess.controller.ChessController;
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
		Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("menu.fxml"));
		Scene scene = new Scene(root);

		stage.setScene(scene);
		stage.show();
	}



	public static void main(String[] args) {
		Chess model = Chess.getInstance();
		ChessController chessController = new ChessController();
		model.getBoard().setChessBoard(chessController.getChessBoard());

		model.addObserver(chessController);

//		chessController.setPieceImages(model.getBoard().getPieceImages());

		Application.launch(args);
	}




}
