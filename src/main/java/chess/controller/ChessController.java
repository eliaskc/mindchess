package chess.controller;

import chess.model.ChessColor;
import chess.model.ChessFacade;
import chess.model.Ply;
import chess.observers.EndGameObserver;
import chess.observers.GameObserver;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
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
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static chess.model.PieceType.*;

/**
 * ChessController handles the chess board
 */
public class ChessController implements Initializable, GameObserver, EndGameObserver {
    double squareDimension = 75;
    double chessboardContainerX;
    double chessboardContainerY;
    int lastClickedX;
    int lastClickedY;
    private ChessFacade model;
    private Parent menuParent;
    private Scene scene;
    private ImageHandler imageHandler;
    private List<ImageView> pieceImages;
    private List<ImageView> legalMoveImages;
    private ImageView kingInCheckImage = new ImageView();
    private List<ImageView> pliesImages = new ArrayList<>();
    private MediaPlayer mediaPlayer;
    private MediaPlayer audioPlayer;
    @FXML
    private MediaView media;
    @FXML
    private Label player1Name;
    @FXML
    private Label player2Name;
    @FXML
    private Label player1Timer;
    @FXML
    private Label player2Timer;
    @FXML
    private Label lblDrawLabel;
    @FXML
    private ImageView chessboardImage;
    @FXML
    private AnchorPane chessboardContainer;
    @FXML
    private AnchorPane drawAnchorPane;
    @FXML
    private FlowPane flowPaneBlackPieces;
    @FXML
    private FlowPane flowPaneWhitePieces;
    @FXML
    private Rectangle player1TimerBox;
    @FXML
    private Rectangle player2TimerBox;
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
    private Button muteUnmuteButton;
    @FXML
    private AnchorPane pliesAnchorPane;
    @FXML
    private ScrollPane pliesScrollPane;
    @FXML
    private FlowPane pliesFlowPane;
    @FXML
    private AnchorPane pliesBoardAnchorPane;
    @FXML
    private ImageView pliesBoardImageView;


    public void setMediaPlayer(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    public void setAudioPlayer(MediaPlayer audioPlayer) {
        this.audioPlayer = audioPlayer;
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
    void goToMenu(ActionEvent event) {
        clearAllPieceImages();
        clearAllLegalMoveImages();
        chessboardContainer.getChildren().remove(kingInCheckImage);
        model.stopAllTimers();
        drawAnchorPane.toBack();
        promotionAnchorPane.toBack();
        pliesAnchorPane.toBack();
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    @FXML
    void forfeit(ActionEvent event) {
        model.getCurrentGame().endGameAsForfeit();
    }

    /**
     * handles if the draw button is clicked. Asks opponent if they want to draw the game,
     * stops players from moving their pieces while the interface is up
     */
    @FXML
    void offerDraw(ActionEvent event) {
        lblDrawLabel.setText(model.getCurrentGame().getCurrentPlayer().getName() + " offered you a draw");
        drawAnchorPane.toFront();
    }

    /**
     * if the opponent refuses the interface will close and allows the player to move their pieces
     */
    @FXML
    void declineDraw(ActionEvent event) {
        drawAnchorPane.toBack();
    }

    /**
     * sets the game to a draw
     */
    @FXML
    void acceptDraw(ActionEvent event) {
        model.getCurrentGame().endGameAsDraw();
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
        chessboardContainerX = chessboardContainer.getLayoutX();
        chessboardContainerY = chessboardContainer.getLayoutY();
    }

    /**
     * Fetches needed values & objects, updates labels and starts timers
     */
    void init() {
        media.setMediaPlayer(mediaPlayer);
        media.setEffect(new GaussianBlur(16));
        endGamePane.toBack();

        chessboardImage.setImage(imageHandler.getChessboardImage());
        updateSquareDimensions();

        pieceImages = imageHandler.fetchPieceImages();
        legalMoveImages = imageHandler.fetchLegalMoveImages();
        drawPieces();
        drawDeadPieces();

        model.getCurrentGame().addGameObserver(this);
        model.getCurrentGame().addEndGameObserver(this);

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
        squareDimension = chessboardImage.getFitHeight() / 8;
        imageHandler.setSquareDimension(squareDimension);
    }

    @FXML
    AnchorPane endGamePane;
    @FXML
    Label endGameLabel;

    @Override
    public void showEndGameResult(String result) {
        Platform.runLater(() -> {
            model.stopAllTimers();
            endGameLabel.setText(result);
            endGamePane.toFront();
        });
    }


    /**
     * Sends to the board that it has been clicked on
     *
     * @param event board clicked
     */
    @FXML
    public void handleClick(MouseEvent event) {
        lastClickedX = translateX(event.getSceneX());
        lastClickedY = translateY(event.getSceneY());
        model.handleBoardInput(lastClickedX, lastClickedY);
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

    @FXML
    private void switchPieceStyle() {
        imageHandler.setMinecraftPieceStyle(!imageHandler.isMinecraftPieceStyle());

        chessboardImage.setImage(imageHandler.getChessboardImage());
        drawLegalMoves();
        drawPieces();
        drawDeadPieces();
        drawPawnPromotionSetup(model.getCurrentGame().getCurrentPlayer().getColor());
    }

    @FXML
    private void muteUnmute() {
        audioPlayer.setMute(!audioPlayer.isMute());
        muteUnmuteButton.setText((muteUnmuteButton.getText().equals("Mute")) ? "Unmute" : "Mute");
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
            chessboardContainer.getChildren().add(pieceImage);
            chessboardContainer.getChildren().get(chessboardContainer.getChildren().indexOf(pieceImage)).setMouseTransparent(true);
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
    public void drawLegalMoves() {
        clearAllLegalMoveImages();

        legalMoveImages = imageHandler.fetchLegalMoveImages();

        for (ImageView imageView : legalMoveImages) {
            ScaleTransition st = new ScaleTransition(Duration.millis(imageHandler.distanceFromMarkedPiece(imageView, lastClickedX, lastClickedY)), imageView);
            st.setFromX(0.1);
            st.setFromY(0.1);
            st.setToX(1);
            st.setToY(1);
            st.setCycleCount(1);
            st.play();

            chessboardContainer.getChildren().add(imageView);
            chessboardContainer.getChildren().get(chessboardContainer.getChildren().indexOf(imageView)).setMouseTransparent(true);
        }
    }

    /**
     * "Lights up" the current player's timer, dims the opponents timer
     */
    @Override
    public void switchedPlayer() {
        if (model.getCurrentGame().getCurrentPlayer() == model.getPlayerWhite()) {
            player1TimerBox.setFill(Color.GREENYELLOW);
            player2TimerBox.setFill(Color.LIGHTGRAY);
        } else {
            player2TimerBox.setFill(Color.GREENYELLOW);
            player1TimerBox.setFill(Color.LIGHTGRAY);
        }
        chessboardContainer.getChildren().remove(kingInCheckImage);
    }

    /**
     * Opens a dialogue box to let the player choose a piece to transform their pawn into
     *
     * @param chessColor
     */
    @Override
    public void pawnPromotionSetup(ChessColor chessColor) {
        promotionAnchorPane.toFront();
        drawPawnPromotionSetup(chessColor);
    }

    private void drawPawnPromotionSetup(ChessColor chessColor) {
        promotionQueen.setImage(imageHandler.createPieceImage(QUEEN, chessColor));
        promotionKnight.setImage(imageHandler.createPieceImage(KNIGHT, chessColor));
        promotionRook.setImage(imageHandler.createPieceImage(ROOK, chessColor));
        promotionBishop.setImage(imageHandler.createPieceImage(BISHOP, chessColor));
    }

    private void pawnPromotion(int x, int y) {
        model.handleBoardInput(x, y);
        promotionAnchorPane.toBack();
    }

    @FXML
    public void handleQueenPromotion() {
        pawnPromotion(20, 0);
    }

    @FXML
    public void handleKnightPromotion() {
        pawnPromotion(21, 0);
    }

    @FXML
    public void handleRookPromotion() {
        pawnPromotion(22, 0);
    }

    @FXML
    public void handleBishopPromotion() {
        pawnPromotion(23, 0);
    }

    private void clearAllPieceImages() {
        chessboardContainer.getChildren().removeAll(pieceImages);
    }

    private void clearAllLegalMoveImages() {
        for (ImageView imageView : legalMoveImages) {
            ScaleTransition st = new ScaleTransition(Duration.millis(75), imageView);
            st.setFromX(1);
            st.setFromY(1);
            st.setToX(0.1);
            st.setToY(0.1);
            st.setCycleCount(1);
            st.play();

            st.setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    chessboardContainer.getChildren().remove(imageView);
                }
            });
        }
    }

    private void clearAllPliesImages() {
        pliesBoardAnchorPane.getChildren().removeAll(pliesImages);
        pliesImages.clear();
    }


    /**
     * Method is called when the "Analyze" button is pressed at the end screen
     */
    @FXML
    public void analyzeGame() {
        populatePliesFlowPane();
        pliesBoardImageView.setImage(imageHandler.getChessboardImage());
        pliesAnchorPane.toFront();
    }

    @FXML
    public void analyzeGameBack() {
        pliesAnchorPane.toBack();
    }

    /**
     * Creates a PlyController object for each ply and populates it with information about the ply
     * Also takes the generated snapshot of that specific ply and creates an image that represents the board after that ply has been made
     */
    private void populatePliesFlowPane() {
        pliesFlowPane.getChildren().clear();
        clearAllPliesImages();

        //Adds the plyControllers to the flowpane and fills the board with respective pieces
        for (Ply ply : model.getCurrentGame().getPlies()) {
            PlyController plyController = new PlyController(ply, model.getCurrentGame().getPlies().indexOf(ply) + 1, imageHandler);
            pliesFlowPane.getChildren().add(plyController);

            //When a ply is clicked all the pieces on the ply board are removed and updated/animated
            plyController.setOnMouseClicked(event -> {
                clearAllPliesImages();
                List<ImageView> plies = plyController.generateBoardImages(true);
                pliesImages.addAll(plies);
                pliesBoardAnchorPane.getChildren().addAll(pliesImages);
            });

            //If this is the first ply, generate the board but dont move the first piece
            if (model.getCurrentGame().getPlies().indexOf(ply) == 0){
                List<ImageView> plies = plyController.generateBoardImages(false);
                pliesImages.addAll(plies);
                pliesBoardAnchorPane.getChildren().addAll(pliesImages);
            }
        }
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
        String min = seconds >= 600 ? "" + seconds / 60 : "0" + seconds / 60;
        return min + ":" + sec;
    }

    @Override
    public void kingInCheck(int x, int y) {
        kingInCheckImage.setImage(imageHandler.createKingInCheckImage());
        kingInCheckImage.setX(x*squareDimension);
        kingInCheckImage.setY(y*squareDimension);
        kingInCheckImage.setOpacity(0.6);
        kingInCheckImage.setMouseTransparent(true);

        chessboardContainer.getChildren().add(kingInCheckImage);
        drawPieces();
    }

    //Game
    public void updateImageHandler() {
        imageHandler.init();
    }
}
