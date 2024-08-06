package es.guepardito.patumusic.view;

import es.guepardito.patumusic.music.SongMetadata;
import es.guepardito.patumusic.music.SongsManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.ByteArrayInputStream;
import java.util.concurrent.TimeUnit;

import static es.guepardito.patumusic.view.AppViewController.formatDuration;

public class IndexCenterController {
    @FXML
    private Label titleLabel;
    @FXML
    private Label artistLabel;
    @FXML
    private Label albumLabel;
    @FXML
    private Label durationLabel;
    @FXML
    private ProgressBar progressBar;
    @FXML
    private ImageView coverArtImageView;

    @FXML
    public void initialize() {
        if (!SongsManager.songs.isEmpty()) {
            setSongMetadata(SongsManager.songs.get(0).getSongMetadata());
        }
    }

    public void setSongMetadata(SongMetadata metadata) {
        titleLabel.setText(metadata.getTitle());
        artistLabel.setText(metadata.getArtist());
        albumLabel.setText(metadata.getAlbum());
        durationLabel.setText(formatDuration(metadata.getDuration()));
        progressBar.setProgress(0);

        byte[] coverArt = metadata.getCoverArt();
        if (coverArt != null) {
            Image image = new Image(new ByteArrayInputStream(coverArt));
            coverArtImageView.setImage(image);
        } else {
            coverArtImageView.setImage(null);
        }
    }

    public void updateProgress(double progress) {
        progressBar.setProgress(progress);
    }



    public void updateCenter(ActionEvent actionEvent) {
        SongMetadata metadata =
                new SongMetadata(
                        "C:\\Users\\rafag\\Music\\ytmusic\\metal 2.0\\" +
                                "Dynazty - Heartless Madness.mp3");
        setSongMetadata(metadata);
    }
}
