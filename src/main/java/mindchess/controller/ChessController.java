package mindchess.controller;

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
import mindchess.model.*;
import mindchess.model.pieces.IPiece;
import mindchess.observers.EndGameObserver;
import mindchess.observers.GameObserver;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * ChessController handles the mindchess board
 * <p>
 * It handeles the fxml files
 * <p>
 * It receves notification from the board when something on the board is changed(moved/removed/..)
 * <p>
 * Recevies input when the user interacts with its scene and does something or send the input to the model to do something
 */
public class ChessController implements Initializable, GameObserver, EndGameObserver {
    private double squareDimension = 75;
    private double chessboardContainerX;
    private double chessboardContainerY;
    private int lastClickedX;
    private int lastClickedY;
    private ChessFacade model;
    private Parent menuParent;
    private Scene scene;
    private ImageHandler imageHandler;
    private List<ImageView> pieceImages;
    private List<ImageView> legalMoveImages;
    private final ImageView kingInCheckImage = new ImageView();
    private final List<ImageView> pliesImages = new ArrayList<>();
    private MediaPlayer mediaPlayer;
    private MediaPlayer audioPlayer;

    //-------------------------------------------------------------------------------------
    //FXML
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
    private Label endGameLabel;
    @FXML
    private Button muteUnmuteButton;
    @FXML
    private Rectangle player1TimerBox;
    @FXML
    private Rectangle player2TimerBox;
    @FXML
    private MediaView media;
    @FXML
    private ImageView chessboardImage;
    @FXML
    private ImageView promotionQueen;
    @FXML
    private ImageView promotionKnight;
    @FXML
    private ImageView promotionRook;
    @FXML
    private ImageView promotionBishop;
    @FXML
    private ImageView pliesBoardImageView;
    @FXML
    private ScrollPane pliesScrollPane;
    @FXML
    private AnchorPane chessboardContainer;
    @FXML
    private AnchorPane drawAnchorPane;
    @FXML
    private AnchorPane promotionAnchorPane;
    @FXML
    private AnchorPane pliesAnchorPane;
    @FXML
    private AnchorPane endGamePane;
    @FXML
    private AnchorPane pliesBoardAnchorPane;
    @FXML
    private FlowPane flowPaneBlackPieces;
    @FXML
    private FlowPane flowPaneWhitePieces;
    @FXML
    private FlowPane pliesFlowPane;

    //-------------------------------------------------------------------------------------
    //start
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

        pieceImages = fetchPieceImages();
        legalMoveImages = imageHandler.fetchLegalMoveImages();
        drawPieces();
        drawDeadPieces();
        model.addGameObserverToCurrentGame(this);
        model.addEndGameObserverToCurrentGame(this);

        player1Name.setText(model.getCurrentPlayerWhiteName());
        player2Name.setText(model.getCurrentPlayerBlackName());
        if (model.getCurrentPlayerColor() == ChessColor.WHITE) {
            player1TimerBox.setFill(Color.GREENYELLOW);
            player2TimerBox.setFill(Color.LIGHTGRAY);
        } else {
            player2TimerBox.setFill(Color.GREENYELLOW);
            player1TimerBox.setFill(Color.LIGHTGRAY);
        }
        model.initTimersInCurrentGame();
    }

    /**
     * Gets the dimensions of the squares from the current size of the chessboard
     */
    private void updateSquareDimensions() {
        squareDimension = chessboardImage.getFitHeight() / 8;
        imageHandler.setSquareDimension(squareDimension);
    }

    public void createMenuScene(Parent menuParent) {
        this.menuParent = menuParent;
        this.scene = new Scene(menuParent);
    }

    //-------------------------------------------------------------------------------------
    //make move

    /**
     * "Lights up" the current player's timer, dims the opponents timer
     */
    @Override
    public void switchedPlayer() {
        if (model.isCurrentPlayerWhite()) {
            player1TimerBox.setFill(Color.GREENYELLOW);
            player2TimerBox.setFill(Color.LIGHTGRAY);
        } else {
            player2TimerBox.setFill(Color.GREENYELLOW);
            player1TimerBox.setFill(Color.LIGHTGRAY);
        }
        chessboardContainer.getChildren().remove(kingInCheckImage);
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
            if (i * squareDimension + chessboardContainerX <= x && x <= chessboardContainerX + squareDimension * (i + 1)) {
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
            if (i * squareDimension + chessboardContainerY <= y && y <= chessboardContainerY + squareDimension * (i + 1)) {
                return i;
            }
        }
        throw new IllegalArgumentException("Outside board");
    }

    //-------------------------------------------------------------------------------------
    //End
    @Override
    public void kingInCheck(int x, int y) {
        kingInCheckImage.setImage(imageHandler.createKingInCheckImage());
        kingInCheckImage.setX(x * squareDimension);
        kingInCheckImage.setY(y * squareDimension);
        kingInCheckImage.setFitHeight(squareDimension);
        kingInCheckImage.setFitWidth(squareDimension);
        kingInCheckImage.setOpacity(0.6);
        kingInCheckImage.setMouseTransparent(true);

        chessboardContainer.getChildren().add(kingInCheckImage);
        drawPieces();
    }

    @Override
    public void showEndGameResult(String result) {
        Platform.runLater(() -> {
            model.stopAllTimers();
            endGameLabel.setText(result);
            endGamePane.toFront();
        });
    }

    @FXML
    void forfeit() {
        model.forfeit();
    }

    /**
     * handles if the draw button is clicked. Asks opponent if they want to draw the game,
     * stops players from moving their pieces while the interface is up
     */
    @FXML
    void offerDraw(ActionEvent event) {
        lblDrawLabel.setText(model.getCurrentPlayerName() + " offered you a draw");
        drawAnchorPane.toFront();
    }

    /**
     * if the opponent refuses the interface will close and allows the player to move their pieces
     */
    @FXML
    void declineDraw() {
        drawAnchorPane.toBack();
    }

    /**
     * sets the game to a draw
     */
    @FXML
    void acceptDraw() {
        model.acceptDraw();
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

    //-------------------------------------------------------------------------------------
    //Draw And Images
    private List<ImageView> fetchPieceImages() {
        List<ImageView> pieceImages = new ArrayList<>();
        for (Map.Entry<Square, IPiece> entry : model.getCurrentBoardMap().entrySet()) {
            ImageView imageView = imageHandler.fetchPieceImageView(entry.getKey(), entry.getValue().getPieceType(), entry.getValue().getColor(), (int) (chessboardContainer.getHeight() / 8));
            pieceImages.add(imageView);
            if (model.getCurrentGamePlies().size() > 0) {
                if (entry.getKey().equals(model.getLastPlyMovedToSquare())) {
                    imageHandler.addTranslateTransition(imageView, model.getLastPlyMovedFromSquare(), model.getLastPlyMovedToSquare(), (int) chessboardContainer.getHeight() / 8, 250);
                }
            }
        }
        return pieceImages;
    }

    /**
     * Draws all pieces from the list of pieceImages from the ImageHandler
     */
    @Override
    public void drawPieces() {
        clearAllPieceImages();

        pieceImages.clear();

        pieceImages = fetchPieceImages();

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
        flowPaneWhitePieces.getChildren().clear();
        flowPaneBlackPieces.getChildren().clear();
        flowPaneWhitePieces.getChildren().addAll(imageHandler.fetchDeadPieceImages(ChessColor.WHITE));
        flowPaneBlackPieces.getChildren().addAll(imageHandler.fetchDeadPieceImages(ChessColor.BLACK));
    }

    /**
     * Draws all legal moves from the list of legalMoves from the ImageHandler
     */
    @Override
    public void drawLegalMoves() {
        clearAllLegalMoveImages();

        legalMoveImages = imageHandler.fetchLegalMoveImages();

        for (ImageView imageView : legalMoveImages) {
            imageHandler.addScaleTransition(imageView, imageHandler.distanceFromMarkedPiece(imageView, lastClickedX, lastClickedY), true);

            chessboardContainer.getChildren().add(imageView);
            chessboardContainer.getChildren().get(chessboardContainer.getChildren().indexOf(imageView)).setMouseTransparent(true);
        }
    }

    @FXML
    private void switchPieceStyle() {
        imageHandler.setMinecraftPieceStyle(!imageHandler.isMinecraftPieceStyle());

        chessboardImage.setImage(imageHandler.getChessboardImage());
        drawLegalMoves();
        drawPieces();
        drawDeadPieces();
        drawPawnPromotionSetup(model.getCurrentPlayerColor());
    }

    //-------------------------------------------------------------------------------------
    //sound
    @FXML
    private void muteUnmute() {
        audioPlayer.setMute(!audioPlayer.isMute());
        muteUnmuteButton.setText(audioPlayer.isMute() ? "Unmute" : "Mute");
    }

    //-------------------------------------------------------------------------------------
    //Promotion

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
        promotionQueen.setImage(imageHandler.createPieceImage(PieceType.QUEEN, chessColor));
        promotionKnight.setImage(imageHandler.createPieceImage(PieceType.KNIGHT, chessColor));
        promotionRook.setImage(imageHandler.createPieceImage(PieceType.ROOK, chessColor));
        promotionBishop.setImage(imageHandler.createPieceImage(PieceType.BISHOP, chessColor));
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

    //-------------------------------------------------------------------------------------
    //Clear
    private void clearAllPieceImages() {
        chessboardContainer.getChildren().removeAll(pieceImages);
    }

    private void clearAllLegalMoveImages() {
        for (ImageView imageView : legalMoveImages) {
            ScaleTransition st = imageHandler.addScaleTransition(imageView, 75, false);

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

    //-------------------------------------------------------------------------------------
    //Analyze

    /**
     * Creates a PlyController object for each ply and populates it with information about the ply
     * Also takes the generated snapshot of that specific ply and creates an image that represents the board after that ply has been made
     */
    private void populatePliesFlowPane() {
        pliesFlowPane.getChildren().clear();
        clearAllPliesImages();

        //Adds the plyControllers to the flowpane and fills the board with respective pieces
        for (Ply ply : model.getCurrentGamePlies()) {
            PlyController plyController = new PlyController(ply, model.getCurrentGamePlies().indexOf(ply) + 1, imageHandler);
            pliesFlowPane.getChildren().add(plyController);

            //When a ply is clicked all the pieces on the ply board are removed and updated/animated
            plyController.setOnMouseClicked(event -> {
                clearAllPliesImages();
                List<ImageView> plies = plyController.generateBoardImages(true);
                pliesImages.addAll(plies);
                pliesBoardAnchorPane.getChildren().addAll(pliesImages);
            });

            //If this is the first ply, generate the board but dont move the first piece
            if (model.getCurrentGamePlies().indexOf(ply) == 0) {
                List<ImageView> plies = plyController.generateBoardImages(false);
                pliesImages.addAll(plies);
                pliesBoardAnchorPane.getChildren().addAll(pliesImages);
            }
        }
    }

    @FXML
    public void analyzeGameBack() {
        pliesAnchorPane.toBack();
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

    //-------------------------------------------------------------------------------------
    //Timer

    /**
     * Fetches the times for each timer from the model when called and updates the labels
     */
    public void updateTimer() {
        Platform.runLater(() -> player1Timer.setText(formatTime(model.getCurrentWhiteTimerTime())));
        Platform.runLater(() -> player2Timer.setText(formatTime(model.getCurrentBlackTimerTime())));
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

    //-------------------------------------------------------------------------------------
    //Getters
    public Scene getMenuScene() {
        return scene;
    }

    //-------------------------------------------------------------------------------------
    //Setters
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
}
