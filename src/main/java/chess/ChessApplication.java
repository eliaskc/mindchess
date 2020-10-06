package chess;

import chess.controller.ChessController;
import chess.controller.ImageHandler;
import chess.controller.MenuController;
import chess.model.ChessFacade;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public final class ChessApplication extends Application {

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setResizable(false);
        FXMLLoader menuLoader = new FXMLLoader(getClass().getClassLoader().getResource("menuView.fxml"));
        FXMLLoader chessLoader = new FXMLLoader(getClass().getClassLoader().getResource("boardView.fxml"));

        Parent menuParent = menuLoader.load();
        Parent chessParent = chessLoader.load();

        MenuController menuController = menuLoader.getController();
        ChessController chessController = chessLoader.getController();
        ImageHandler imageHandler = new ImageHandler();

        menuController.createChessScene(chessParent);
        chessController.createMenuScene(menuParent);

        menuController.setChessController(chessController);

        ChessFacade model = new ChessFacade();
        menuController.setModel(model);
        chessController.setModel(model);
        imageHandler.setModel(model);
        model.addObserver(chessController);

        chessController.setImageHandler(imageHandler);

        //Might need to be reworked since our menu scene is created in our chessController which is kinda weird
        stage.setScene(chessController.getMenuScene());
        stage.show();
    }
}
