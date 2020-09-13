package chess.controller;

import chess.Observer;
import chess.model.Chess;
import chess.model.pieces.Piece;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ChessController implements Initializable, Observer {

    //Controller should not have model. FIX
    Chess model = Chess.getInstance();

    //Should not be dependent on model. FIX
    List<ImageView> pieceImages = model.getBoard().getPieceImages();

    @FXML Button btnBack;
    @FXML private Label player1Name;
    @FXML private Label player2Name;
    @FXML private Label player1Timer;
    @FXML private Label player2Timer;
    @FXML private ImageView chessBoard;
    @FXML private AnchorPane chessBoardContainer;


    @FXML
    void goToMenu (ActionEvent event) throws IOException {
        Parent parent = FXMLLoader.load(getClass().getClassLoader().getResource("menu.fxml"));
        Scene scene = new Scene(parent);

        Stage window = (Stage) ((Node)event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    
    // Implement observer !!!!!!!
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        player1Name.setText(model.getPlayer1().getName());
        player2Name.setText(model.getPlayer2().getName());
        drawPieces();
    }

    @FXML
    public void findSquare(MouseEvent event){                               //Find the square you clicked on and do something about it.
        model.findSquare(event.getSceneX(),event.getSceneY());
    }

    public ImageView getChessBoard() {
        return chessBoard;
    }

    public void setPieceImages(List<ImageView> pieceImages) {
        this.pieceImages = pieceImages;
    }

    public void drawPieces() {
        for (ImageView pieceImage : pieceImages) {
            chessBoardContainer.getChildren().add(pieceImage);
        }
    }

    @Override
    public void onAction() {
        drawPieces();
    }
}
