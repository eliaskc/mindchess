package mindchess.controller;

import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
import mindchess.model.*;
import mindchess.model.enums.ChessColor;
import mindchess.model.enums.PieceType;
import mindchess.model.pieces.IPiece;
import mindchess.observers.EndGameObserver;
import mindchess.observers.GameObserver;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Handles the view of the ongoing game
 *
 * If an input is made on the board, it is sent as input to the model so it can be handle properly
 *
 * It receives notification from the board when something on the board changes (piece moves, legal moves are found etc)
 */
public class MindchessController implements Initializable, GameObserver, EndGameObserver {
    private double squareDimension = 75;
    private double chessboardContainerX;
    private double chessboardContainerY;
    private int lastClickedX;
    private int lastClickedY;
    private ChessFacade model;
    private Scene scene;
    private ImageHandlerUtil imageHandlerUtil;
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
    private Button drawButton;
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
     * Initializes the mindchess view.
     *   - sets the media player for the view & adds blur to the background video
     *   - hides dialogue boxes
     *   - sets the image for the chessboard
     *   - gets the initial (or current if an active game is loaded) pieces and legal moves
     *   - adds the controller as observer of the game
     *   - updates the labels for the player names
     *   - highlights and initializes the players' timers
     *
     */
    void init() {
        media.setMediaPlayer(mediaPlayer);
        media.setEffect(new GaussianBlur(16));
        endGamePane.toBack();

        chessboardImage.setImage(imageHandlerUtil.getChessboardImage());
        updateSquareDimensions();

        pieceImages = fetchPieceImages();
        legalMoveImages = imageHandlerUtil.fetchLegalMoveImages();
        drawPieces();
        drawDeadPieces();
        drawLegalMoves();

        model.addGameObserverToCurrentGame(this);
        model.addEndGameObserverToCurrentGame(this);

        player1Name.setText(model.getCurrentWhitePlayerName());
        player2Name.setText(model.getCurrentBlackPlayerName());
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
        imageHandlerUtil.setSquareDimension(squareDimension);
    }

    public void createMenuScene(Parent menuParent) {
        this.scene = new Scene(menuParent);
    }

    void setDisableDrawButton(boolean disableDrawButton){
        drawButton.setDisable(disableDrawButton);
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
     * Delegates the input from a click on the board to the model to be handled
     *
     * @param event the object representing the click
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
     * @return the x square index of the click
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
     * @return the y square index of the click
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

    /**
     * Marks the square with the coordinates with a red background
     * @param x the x coordinate of the square to be marked
     * @param y the x coordinate of the square to be marked
     */
    @Override
    public void kingInCheck(int x, int y) {
        kingInCheckImage.setImage(imageHandlerUtil.createKingInCheckImage());
        kingInCheckImage.setX(x * squareDimension);
        kingInCheckImage.setY(y * squareDimension);
        kingInCheckImage.setFitHeight(squareDimension);
        kingInCheckImage.setFitWidth(squareDimension);
        kingInCheckImage.setOpacity(0.6);
        kingInCheckImage.setMouseTransparent(true);

        chessboardContainer.getChildren().add(kingInCheckImage);
        drawPieces();
    }

    /**
     * Shows a dialogue box with the game result as well as buttons to return to the menu or analyze the game
     * @param result the result of the game
     */
    @Override
    public void showEndGameResult(String result) {
        Platform.runLater(() -> {
            model.stopAllTimers();
            endGameLabel.setText(result);
            endGamePane.toFront();
        });
    }

    /**
     * Ends the game as a forfeit for the current player
     */
    @FXML
    void forfeit() {
        model.forfeit();
    }

    /**
     * Opens a dialogue box when someone offers their opponents a draw, where the opponents can then choose
     * to decline or accept
     */
    @FXML
    void offerDraw() {
        lblDrawLabel.setText(model.getCurrentPlayerName() + " offered you a draw");
        drawAnchorPane.toFront();
    }

    /**
     * Hides the "Draw offer" dialogue box if the draw is declined
     */
    @FXML
    void declineDraw() {
        drawAnchorPane.toBack();
    }

    /**
     * Ends the game as a draw
     */
    @FXML
    void acceptDraw() {
        model.acceptDraw();
    }

    /**
     * Switches to the menu/start screen scene
     *
     * @param event Button click
     */
    @FXML
    void goToMenu(ActionEvent event) {
        clearAllPieceImages();
        clearAllLegalMoveImages();
        chessboardContainer.getChildren().remove(kingInCheckImage);
        model.removeGameObserverFromCurrentGame(this);
        model.removeEndGameObserverFromCurrentGame(this);
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

    /**
     * Creates a list with ImageViews of the currently active pieces on the board.
     *  For each piece it uses the ImageHandlerUtil in order to add an animation to the ImageView
     * @return list of ImageViews of all pieces
     */
    private List<ImageView> fetchPieceImages() {
        List<ImageView> pieceImages = new ArrayList<>();
        for (Map.Entry<Square, IPiece> entry : model.getCurrentBoardMap().entrySet()) {
            ImageView imageView = imageHandlerUtil.createPieceImageView(entry.getKey(), entry.getValue().getPieceType(), entry.getValue().getColor(), (int) (chessboardContainer.getHeight() / 8));
            pieceImages.add(imageView);
            if (model.getCurrentGamePlies().size() > 0) {
                if (entry.getKey().equals(model.getLastPlyMovedToSquare())) {
                    imageHandlerUtil.addTranslateTransition(imageView, model.getLastPlyMovedFromSquare(), model.getLastPlyMovedToSquare(), (int) chessboardContainer.getHeight() / 8, 250);
                }
            }
        }
        return pieceImages;
    }

    /**
     * Draws the currently active pieces fetched from fetchPieceImages
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
     * Draws the dead pieces for each colors, which it fetches from ImageHandlerUtil
     */
    @Override
    public void drawDeadPieces() {
        flowPaneWhitePieces.getChildren().clear();
        flowPaneBlackPieces.getChildren().clear();
        flowPaneWhitePieces.getChildren().addAll(imageHandlerUtil.fetchDeadPieceImages(ChessColor.WHITE));
        flowPaneBlackPieces.getChildren().addAll(imageHandlerUtil.fetchDeadPieceImages(ChessColor.BLACK));
    }

    /**
     * Draws all legal moves from the list of legalMoves from the ImageHandler
     */
    @Override
    public void drawLegalMoves() {
        clearAllLegalMoveImages();

        legalMoveImages = imageHandlerUtil.fetchLegalMoveImages();

        for (ImageView imageView : legalMoveImages) {
            imageHandlerUtil.addScaleTransition(imageView, imageHandlerUtil.distanceFromMarkedPiece(imageView, lastClickedX, lastClickedY), true);

            chessboardContainer.getChildren().add(imageView);
            chessboardContainer.getChildren().get(chessboardContainer.getChildren().indexOf(imageView)).setMouseTransparent(true);
        }
    }

    /**
     * Switches between then standard chess style and the custom Minecraft style.
     * Called when the "Switch style" button is pressed
     */
    @FXML
    private void switchPieceStyle() {
        imageHandlerUtil.setMinecraftPieceStyle(!imageHandlerUtil.isMinecraftPieceStyle());

        chessboardImage.setImage(imageHandlerUtil.getChessboardImage());
        drawLegalMoves();
        drawPieces();
        drawDeadPieces();
        drawPawnPromotionSetup(model.getCurrentPlayerColor());
    }

    //-------------------------------------------------------------------------------------
    //Sound
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
     * @param chessColor the color of the current player
     */
    @Override
    public void pawnPromotionSetup(ChessColor chessColor) {
        promotionAnchorPane.toFront();
        drawPawnPromotionSetup(chessColor);
    }

    private void drawPawnPromotionSetup(ChessColor chessColor) {
        promotionQueen.setImage(imageHandlerUtil.createPieceImage(PieceType.QUEEN, chessColor));
        promotionKnight.setImage(imageHandlerUtil.createPieceImage(PieceType.KNIGHT, chessColor));
        promotionRook.setImage(imageHandlerUtil.createPieceImage(PieceType.ROOK, chessColor));
        promotionBishop.setImage(imageHandlerUtil.createPieceImage(PieceType.BISHOP, chessColor));
    }


    private void pawnPromotion(int x) {
        model.handleBoardInput(x, 0);

    }

    @Override
    public void pawnPromotionCleanUp(){
        promotionAnchorPane.toBack();
    }

    @FXML
    public void handleQueenPromotion() {
        pawnPromotion(20);
    }

    @FXML
    public void handleKnightPromotion() {
        pawnPromotion(21);
    }

    @FXML
    public void handleRookPromotion() {
        pawnPromotion(22);
    }

    @FXML
    public void handleBishopPromotion() {
        pawnPromotion(23);
    }

    //-------------------------------------------------------------------------------------
    //Clear
    private void clearAllPieceImages() {
        chessboardContainer.getChildren().removeAll(pieceImages);
    }

    private void clearAllLegalMoveImages() {
        for (ImageView imageView : legalMoveImages) {
            ScaleTransition st = imageHandlerUtil.addScaleTransition(imageView, 75, false);

            st.setOnFinished(actionEvent -> chessboardContainer.getChildren().remove(imageView));
        }
        legalMoveImages.clear();
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

        //Adds the plyControllers to the flow pane and fills the board with respective pieces
        for (Ply ply : model.getCurrentGamePlies()) {
            PlyController plyController = new PlyController(ply, model.getCurrentGamePlies().indexOf(ply) + 1, imageHandlerUtil);
            pliesFlowPane.getChildren().add(plyController);

            //When a ply is clicked all the pieces on the ply board are removed and updated/animated
            plyController.setOnMouseClicked(event -> {
                clearAllPliesImages();
                List<ImageView> plies = plyController.generateBoardImages(true);
                pliesImages.addAll(plies);
                pliesBoardAnchorPane.getChildren().addAll(pliesImages);
            });

            //If this is the first ply, this code generates the board but doesn't move the first piece
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
     * Shows a game analysis view when the "Analyze" button is pressed after a game has ended
     */
    @FXML
    public void analyzeGame() {
        populatePliesFlowPane();
        pliesBoardImageView.setImage(imageHandlerUtil.getChessboardImage());
        pliesAnchorPane.toFront();
    }

    //-------------------------------------------------------------------------------------
    //Timer

    /**
     * Fetches the times for each timer from the model when called and updates the labels
     */
    @Override
    public void updateTimer() {
        Platform.runLater(() -> player1Timer.setText(formatTime(model.getCurrentWhiteTimerTime())));
        Platform.runLater(() -> player2Timer.setText(formatTime(model.getCurrentBlackTimerTime())));
    }

    /**
     * Takes an input in seconds and formats it into minutes and seconds, mm:ss as a String
     *
     * Appends zeroes when needed to maintain the 4 digit structure
     *
     * @param seconds time to format
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

    public void setImageHandler(ImageHandlerUtil imageHandlerUtil) {
        this.imageHandlerUtil = imageHandlerUtil;
    }
}
