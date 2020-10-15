package chess.controller;

import chess.model.ChessFacade;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

/**
 * MenuController handles the menu
 */
public class MenuController implements Initializable {
    private ChessFacade model;

    private Parent chessParent;
    private Scene scene;

    private final String media_URL_1 = "/backgroundVideos/background_video_1.mp4";
    private final String media_URL_2 = "/backgroundVideos/background_video_2.mp4";
    private final String media_URL_3 = "/backgroundVideos/background_video_3.mp4";
    private final String media_URL_4 = "/backgroundVideos/background_video_4.mp4";
    List<String> media_list = Arrays.asList(media_URL_1, media_URL_2, media_URL_3, media_URL_4);

    private final String audio_URL_1 = "/backgroundMusic/C418_Sweden.mp3";
    private final String audio_URL_2 = "/backgroundMusic/C418_SubwooferLullaby.mp3";
    //private final String audio_URL_3 = "/backgroundMusic/CaptainSparklez_Revenge.mp3";
    List<String> audio_list = Arrays.asList(audio_URL_1, audio_URL_2);

    private MediaPlayer mediaPlayer;
    private MediaPlayer audioPlayer;

    private ChessController chessController;
    private final HashMap<String, Integer> timerMap = new LinkedHashMap<>();
    @FXML
    private MediaView media;
    @FXML
    private AnchorPane rootAnchor;
    @FXML
    private ImageView btnStart;
    @FXML
    private ImageView btnExit;
    @FXML
    private TextField player1NameField;
    @FXML
    private TextField player2NameField;
    @FXML
    private Label timeLabel;
    @FXML
    private ComboBox btnTimerDrop;

    public void setChessController(ChessController chessController) {
        this.chessController = chessController;
    }

    public void setModel(ChessFacade model) {
        this.model = model;
    }

    /**
     * Gets the inputs from the start page and switches to the board scene, and brings the inputs with it
     * <p>
     * Happens when you click the start button
     *
     * @param event Clicked the button
     */
    @FXML
    void goToBoard(ActionEvent event) {
        //Does not create a new boardmap
        model.createNewGame();
        model.getCurrentGame().initGame();

        chessController.updateImageHandler();

        if (!player1NameField.getText().equals("")) model.getPlayerWhite().setName(player1NameField.getText());
        if (!player2NameField.getText().equals("")) model.getPlayerBlack().setName(player2NameField.getText());
        model.getPlayerWhite().getTimer().setTime(timerMap.get(btnTimerDrop.getValue()));
        model.getPlayerBlack().getTimer().setTime(timerMap.get(btnTimerDrop.getValue()));

        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

        chessController.setMediaPlayer(mediaPlayer);
        chessController.setAudioPlayer(audioPlayer);
        chessController.init();
        chessController.drawPieces();
    }

    public void createChessScene(Parent chessParent) {
        this.chessParent = chessParent;
        this.scene = new Scene(chessParent);
    }

    /**
     * Exits the application when called
     *
     * @param event Pressed the button
     */
    @FXML
    void Exit(ActionEvent event) {
        System.exit(0);
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeBackgroundMusic();
        initializeBackgroundVideo();
        initTimer();
    }

    /**
     * Fetches a random video from /background_videos/ and sets it as the background in our root
     */
    private void initializeBackgroundVideo() {
        Random ran = new Random();
        int videoIndex = ran.nextInt(4);
        mediaPlayer = new MediaPlayer(new Media(getClass().getResource(media_list.get(videoIndex)).toExternalForm()));
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(1000);
        media.setMediaPlayer(mediaPlayer);
    }

    /**
     * Fetches a random music from /background_music/ and sets it as background music
     */
    private void initializeBackgroundMusic() {
        Random ran = new Random();
        int audioIndex = ran.nextInt(2);
        audioPlayer = new MediaPlayer(new Media(getClass().getResource(audio_list.get(audioIndex)).toExternalForm()));
        audioPlayer.setAutoPlay(true);
        audioPlayer.setVolume(0.5);
        audioPlayer.setOnEndOfMedia(() -> {
            int audioIndex2 = ran.nextInt(2);
            audioPlayer = new MediaPlayer(new Media(getClass().getResource(audio_list.get(audioIndex2)).toExternalForm()));
        });
    }

    /**
     * creates the timermap and gives the dropdown menu its options
     * <p>
     * Where the possible times is decided
     */
    private void initTimer() {
        timerMap.put("1 min", 5);
        timerMap.put("3 min", 180);
        timerMap.put("5 min", 300);
        timerMap.put("10 min", 600);
        timerMap.put("30 min", 1800);
        timerMap.put("60 min", 3600);

        timerMap.forEach((key, value) -> btnTimerDrop.getItems().add(key));

        btnTimerDrop.getSelectionModel().selectFirst();
    }
}
