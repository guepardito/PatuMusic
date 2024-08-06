package es.guepardito.patumusic.view;

import es.guepardito.patumusic.music.Song;
import es.guepardito.patumusic.music.SongMetadata;
import es.guepardito.patumusic.music.SongsManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class IndexController {

    // LEFT
    public ListView<String> songListView;
    public Button addSong;
    public Button deleteSong;
    public Button selectSong;

    // CENTER
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

    }

    // CENTER
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

    public String formatDuration(long millis) {
        return String.format(
                "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );

    }

    public void playSong(ActionEvent actionEvent) {

    }
    // END OF CENTER

    // LEFT
    public void handleAddSongs(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.setInitialDirectory(new File("."));
        try {
            File[] files = fileChooser.showOpenMultipleDialog(null).toArray(new File[0]);
            for (File file : files) {
                Song song = new Song(file.getAbsolutePath());
                SongsManager.songs.add(song);
                songListView.getItems().add(song.toString());
            }
            System.out.println(SongsManager.songs);
        } catch (NullPointerException npe) {
            System.out.println("No se puede abrir el archivo");
        }
    }

    public void handleRemoveSongs(ActionEvent actionEvent) {
        SongsManager.songs.removeIf(
                song -> song.getSongMetadata().getTitle()
                        .equals(songListView.getItems()
                                .get(songListView.getSelectionModel().getSelectedIndex())
                        )
        );
        songListView.getItems().remove(songListView.getSelectionModel().getSelectedItem());
    }

    public void handleSelectSongs(ActionEvent actionEvent)  {
        setSongMetadata(SongsManager.songs.get(songListView.getSelectionModel().getSelectedIndex()).getSongMetadata());
    }
    // END OF LEFT
}
