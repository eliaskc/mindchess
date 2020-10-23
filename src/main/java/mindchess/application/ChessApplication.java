package mindchess.application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;
import mindchess.controller.MindchessController;
import mindchess.controller.ImageHandlerUtil;
import mindchess.controller.MenuController;
import mindchess.model.ChessFacade;

/**
 * Class for the application
 * <p>
 * Starts the application
 */
public final class ChessApplication extends Application {

    /**
     * Launches the program
     *
     * @param args
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    /**
     * Initialize controllers, ImageHandlerUtil and fxml files
     * <p>
     * Creates a ChessFacade/model and sends it into the controllers
     *
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        stage.setResizable(false);
        FXMLLoader menuLoader = new FXMLLoader(getClass().getClassLoader().getResource("menuView.fxml"));
        FXMLLoader chessLoader = new FXMLLoader(getClass().getClassLoader().getResource("mindchessView.fxml"));

        Parent menuParent = menuLoader.load();
        Parent chessParent = chessLoader.load();

        MenuController menuController = menuLoader.getController();
        MindchessController mindchessController = chessLoader.getController();
        ImageHandlerUtil imageHandlerUtil = new ImageHandlerUtil();

        menuController.createChessScene(chessParent);
        mindchessController.createMenuScene(menuParent);

        menuController.setChessController(mindchessController);

        ChessFacade model = new ChessFacade();
        menuController.setModel(model);
        mindchessController.setModel(model);
        imageHandlerUtil.setModel(model);

        mindchessController.setImageHandler(imageHandlerUtil);

        stage.setScene(mindchessController.getMenuScene());
        stage.show();
    }
}
