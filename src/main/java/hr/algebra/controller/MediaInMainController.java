package hr.algebra.controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import java.net.URL;
import java.util.ResourceBundle;

public class MediaInMainController implements Initializable {

    @FXML
    private MediaView mediaView;

    @FXML
    private Pane videoWrapper;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try{
            String videoPath = getClass().getResource("/video/noviPromoVideo.mp4").toExternalForm();

            Media media = new Media(videoPath);
            MediaPlayer mediaPlayer = new MediaPlayer(media);

            javafx.scene.shape.Rectangle clip = new javafx.scene.shape.Rectangle(800, 450);
            clip.setArcWidth(40);
            clip.setArcHeight(40);
            videoWrapper.setClip(clip);

            mediaView.setMediaPlayer(mediaPlayer);
            mediaView.setFitHeight(500);
            mediaView.setFitWidth(800);

            mediaPlayer.setVolume(0.3);
            mediaPlayer.setAutoPlay(true);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.play();
        }catch(Exception ex){
            System.err.println("Problem s videom " + ex.getMessage());
        }

    }
}
