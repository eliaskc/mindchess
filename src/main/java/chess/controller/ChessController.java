package chess.controller;

import chess.GameObserver;

import chess.model.ChessColor;
import chess.model.ChessFacade;
import chess.model.PieceType;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import static chess.model.PieceType.*;

/**
 * ChessController handles the chess board
 */
public class ChessController implements Initializable, GameObserver {
    double squareDimension = 75;
    double chessboardContainerX;
    double chessboardContainerY;
    private ChessFacade model;
    private Parent menuParent;
    private Scene scene;
    private ImageHandler imageHandler;
    private List<ImageView> pieceImages;
    private List<ImageView> legalMoveImages;
    private MediaPlayer mediaPlayer;
    @FXML
    private MediaView media;
    @FXML
    private ImageView btnBack;
    @FXML
    private Label player1Name;
    @FXML
    private Label player2Name;
    @FXML
    private Label player1Timer;
    @FXML
    private Label player2Timer;
    @FXML
    private ImageView chessBoardImage;
    @FXML
    private AnchorPane chessBoardContainer;
    @FXML
    private FlowPane flowPaneBlackPieces;
    @FXML
    private FlowPane flowPaneWhitePieces;
    @FXML
    private Rectangle player1TimerBox;
    @FXML
    private Rectangle player2TimerBox;
    @FXML
    private Button btnMenuEndGame;
    @FXML
    private AnchorPane promotionAnchorPane;
    @FXML
    private ImageView promotionQueen;
    @FXML
    private ImageView promotionKnight;
    @FXML
    private ImageView promotionRook;
    @FXML
    private ImageView promotionBishop;
    @FXML
    private ImageView btnForfeit;
    @FXML
    private ImageView btnDraw;


    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public void setModel(ChessFacade model) {
        this.model = model;
    }

    public void setImageHandler(ImageHandler imageHandler) {
        this.imageHandler = imageHandler;
    }

    /**
     * Switches to the menu/startscreen scene
     *
     * @param event Button click
     */
    @FXML
    void goToMenu(MouseEvent event) {
        clearAllPieceImages();
        clearAllLegalMoveImages();
        promotionAnchorPane.toBack();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    void forfeitClicked(MouseEvent event) {
        model.getCurrentGame().onePlayerForfeit();
    }

    @FXML
    void drawGameClicked(MouseEvent event) {
        model.getCurrentGame().drawGameClicked();
    }

    public void createMenuScene(Parent menuParent) {
        this.menuParent = menuParent;
        this.scene = new Scene(menuParent);
    }

    public Scene getMenuScene() {
        return scene;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        chessboardContainerX = chessBoardContainer.getLayoutX();
        chessboardContainerY = chessBoardContainer.getLayoutY();
    }

    /**
     * Fetches needed values & objects, updates labels and starts timers
     */
    void init() {
        media.setMediaPlayer(mediaPlayer);
        media.setEffect(new GaussianBlur(18));
        endGamePane.toBack();

        updateSquareDimensions();

        pieceImages = imageHandler.fetchPieceImages();
        legalMoveImages = imageHandler.fetchLegalMoveImages();
        drawPieces();
        drawDeadPieces();

        model.getCurrentGame().addObserver(this);

        player1Name.setText(model.getPlayerWhite().getName());
        player2Name.setText(model.getPlayerBlack().getName());
        player1TimerBox.setFill(Color.GREENYELLOW);
        player2TimerBox.setFill(Color.LIGHTGRAY);
        model.getCurrentGame().initTimers();
    }

    /**
     * Gets the dimensions of the squares from the current size of the chessboard
     */
    private void updateSquareDimensions() {
        squareDimension = chessBoardImage.getFitHeight() / 8;
        imageHandler.setSquareDimension(squareDimension);
    }

    @FXML AnchorPane endGamePane;
    @FXML Label endGameLabel;

    @Override
    public void checkEndGame(String result) {
        Platform.runLater(() -> {
            if(result.equals("white")){
                endGameLabel.setText(player1Name.getText() + " wins");
                endGamePane.toFront();
                model.endGame();
            }
            else if(result.equals("black")){
                endGameLabel.setText(player2Name.getText() + " wins");
                endGamePane.toFront();
                model.endGame();
            }
            else if(result.equals("draw")){
                endGameLabel.setText("Game draw");
                endGamePane.toFront();
                model.endGame();
            }
        });
    }

    /**
     * Sends to the board that it has been clicked on
     *
     * @param event board clicked
     */
    @FXML
    public void handleClick(MouseEvent event) {
        model.handleBoardClick(translateX(event.getSceneX()), translateY(event.getSceneY()));
    }

    /**
     * Translate input x coordinate into a square index x
     *
     * @param x coordinate of click
     * @return
     */
    private int translateX(double x) {
        for (int i = 0; i < 8; i++) {
            if ((i * squareDimension + chessboardContainerX <= x && x <= chessboardContainerX + squareDimension * (i + 1))) {
                return i;
            }
        }
        throw new IllegalArgumentException("Outside board");
    }

    /**
     * Translate input y coordinate into a square index y
     *
     * @param y coordinate of click
     * @return
     */
    private int translateY(double y) {
        for (int i = 0; i < 8; i++) {
            if ((i * squareDimension + chessboardContainerY <= y && y <= chessboardContainerY + squareDimension * (i + 1))) {
                return i;
            }
        }
        throw new IllegalArgumentException("Outside board");
    }

    /**
     * Draws all pieces from the list of pieceImages from the ImageHandler
     */
    @Override
    public void drawPieces() {
        imageHandler.fetchPieceImages();
        imageHandler.updateImageCoordinates();

        clearAllPieceImages();

        //Clears the list of old piece locations/images and updates it with the new one
        imageHandler.getPieceImages().clear();
        pieceImages = imageHandler.fetchPieceImages();

        for (ImageView pieceImage : pieceImages) {
            chessBoardContainer.getChildren().add(pieceImage);
            chessBoardContainer.getChildren().get(chessBoardContainer.getChildren().indexOf(pieceImage)).setMouseTransparent(true);
        }
    }

    /**
     * Draws all dead pieces from the ImageHandler
     */
    @Override
    public void drawDeadPieces() {
        imageHandler.fetchDeadPieceImages();
        flowPaneWhitePieces.getChildren().clear();
        flowPaneBlackPieces.getChildren().clear();
        flowPaneWhitePieces.getChildren().addAll(imageHandler.getWhiteImageViews());
        flowPaneBlackPieces.getChildren().addAll(imageHandler.getBlackImageViews());
    }

    /**
     * Draws all legal moves from the list of legalMoves from the ImageHandler
     */
    @Override
    public void drawLegalMoves(){
        clearAllLegalMoveImages();

        legalMoveImages = imageHandler.fetchLegalMoveImages();

        for (ImageView imageView : legalMoveImages) {
            chessBoardContainer.getChildren().add(imageView);
            chessBoardContainer.getChildren().get(chessBoardContainer.getChildren().indexOf(imageView)).setMouseTransparent(true);
        }
    }

    /**
     * "Lights up" the current player's timer, dims the opponents timer
     */
    @Override
    public void switchedPlayer() {
        if (model.getCurrentGame().getCurrentPlayer() == model.getPlayerWhite()){
            player1TimerBox.setFill(Color.GREENYELLOW);
            player2TimerBox.setFill(Color.LIGHTGRAY);
        } else {
            player2TimerBox.setFill(Color.GREENYELLOW);
            player1TimerBox.setFill(Color.LIGHTGRAY);
        }
    }

    /**
     * Opens a dialogue box to let the player choose a piece to transform their pawn into
     * @param chessColor
     */
    @Override
    public void pawnPromotionSetup(ChessColor chessColor) {
        promotionAnchorPane.toFront();
        promotionQueen.setImage(imageHandler.createPieceImage(QUEEN, chessColor));
        promotionKnight.setImage(imageHandler.createPieceImage(KNIGHT, chessColor));
        promotionRook.setImage(imageHandler.createPieceImage(ROOK, chessColor));
        promotionBishop.setImage(imageHandler.createPieceImage(BISHOP, chessColor));
    }

    private void pawnPromotion(PieceType pieceType) {
        model.getCurrentGame().pawnPromotion(pieceType);
        promotionAnchorPane.toBack();
    }

    @FXML
    public void handleQueenPromotion(){
        pawnPromotion(QUEEN);
    }

    @FXML
    public void handleKnightPromotion(){
        pawnPromotion(KNIGHT);
    }

    @FXML
    public void handleRookPromotion(){
        pawnPromotion(ROOK);
    }

    @FXML
    public void handleBishopPromotion(){
        pawnPromotion(BISHOP);
    }

    private void clearAllPieceImages() {
        chessBoardContainer.getChildren().removeAll(pieceImages);
    }

    private void clearAllLegalMoveImages() {
        chessBoardContainer.getChildren().removeAll(legalMoveImages);
    }

    /**
     * Fetches the times for each timer from the model when called and updates the labels
     */
    public void updateTimer() {
        Platform.runLater(() -> player1Timer.setText(formatTime(model.getPlayerWhite().getTimer().getTime())));
        Platform.runLater(() -> player2Timer.setText(formatTime(model.getPlayerBlack().getTimer().getTime())));
    }

    /**
     * Takes an input integer seconds and formats it into minutes and seconds xx:xx as a String
     * appends zeroes when needed to maintain the 4 digit structure
     *
     * @param seconds
     * @return the formatted time
     */
    private String formatTime(int seconds) {
        String sec = seconds % 60 >= 10 ? "" + (seconds % 3600) % 60 : "0" + (seconds % 3600) % 60;
        String min = seconds >= 600 ? "" + (seconds % 3600) / 60 : "0" + (seconds % 3600) / 60;
        return min + ":" + sec;
    }

    //Game
    public void updateImageHandler() {
        imageHandler.init();
    }
}
