package es.guepardito.patumusic.view;

import es.guepardito.patumusic.music.Song;
import es.guepardito.patumusic.music.SongMetadata;
import es.guepardito.patumusic.music.SongsManager;
import javafx.beans.InvalidationListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.concurrent.TimeUnit;

import static es.guepardito.patumusic.Application.DISCORD_MANAGER;
import static es.guepardito.patumusic.music.SongsManager.actualSongIndex;

public class IndexController {
    private InvalidationListener volumeListener;
    private InvalidationListener progressListener;
    // LEFT
    @FXML
    public ListView<String> songListView;
    @FXML
    public Button addSong;
    @FXML
    public Button deleteSong;
    @FXML
    public Button selectSong;

    // CENTER
    @FXML
    private Label titleLabel;
    @FXML
    private Label artistLabel;
    @FXML
    private Label albumLabel;
    @FXML
    public Label elapsedTimeLabel;
    @FXML
    private Label totalDurationLabel;
    @FXML
    private Slider progressSlider;
    @FXML
    private ImageView coverArtImageView;
    @FXML
    public Slider volumeSlider;

    @FXML
    public void initialize() {

    }

    // CENTER
    public void setSongMetadata(SongMetadata metadata) {
        titleLabel.setText(metadata.getTitle());
        artistLabel.setText(metadata.getArtist());
        albumLabel.setText(metadata.getAlbum());
        totalDurationLabel.setText(formatDuration(metadata.getDuration()));
        progressSlider.setValue(0.01);
        volumeSlider.setValue(SongsManager.songs.get(getSelectedIndex()).getVolume() * 100);

        byte[] coverArt = metadata.getCoverArt();
        if (coverArt != null) {
            Image image = new Image(new ByteArrayInputStream(coverArt));
            coverArtImageView.setImage(image);
        } else {
            coverArtImageView.setImage(null);
        }
    }

    public void setElapsedTime(double elapsedTime) {
        elapsedTimeLabel.setText(formatDuration(elapsedTime));
    }

    public String formatDuration(double millisDouble) {
        long millis = (long) (millisDouble);
        return String.format(
                "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );
    }

    public void handlePlayPauseSong(ActionEvent actionEvent) {
        if (!SongsManager.isPlaying) {
            SongsManager.songs.get(actualSongIndex).play();
            DISCORD_MANAGER.setDiscordActivity(
                    SongsManager.getActualSongMetadata().getTitle(),
                    SongsManager.getActualSongMetadata().getArtist(),
                    SongsManager.getActualSongMetadata().getCoverArtAsString(),
                    (long) (SongsManager.getActualSongMetadata().getDuration()
                            - SongsManager.songs.get(actualSongIndex).getMediaPlayer().getCurrentTime().toMillis())
            );
        } else {
            SongsManager.songs.get(actualSongIndex).pause();

            DISCORD_MANAGER.setDiscordActivity(
                    SongsManager.getActualSongMetadata().getTitle(),
                    SongsManager.getActualSongMetadata().getArtist(),
                    SongsManager.getActualSongMetadata().getCoverArtAsString()
            );
        }
    }

    public void handlerestartSong(ActionEvent actionEvent) {
        SongsManager.songs.get(actualSongIndex).restart();
        DISCORD_MANAGER.setDiscordActivity(
                SongsManager.getActualSongMetadata().getTitle(),
                SongsManager.getActualSongMetadata().getArtist(),
                SongsManager.getActualSongMetadata().getCoverArtAsString(),
                SongsManager.getActualSongMetadata().getDuration()
        );
    }

    public void handleSkipSong(ActionEvent actionEvent) {
        prepareNextSong();
        setNextSongListener();
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
            npe.printStackTrace();
        }
    }

    public void handleRemoveSongs(ActionEvent actionEvent) {
        SongsManager.songs.removeIf(
                song -> song.getSongMetadata().getTitle()
                        .equals(songListView.getItems()
                                .get(getSelectedIndex())
                        )
        );
        songListView.getItems().remove(songListView.getSelectionModel().getSelectedItem());
    }

    // TODO: refactorizar para poder usar sus funciones en otras partes
    public void handleSelectSong(ActionEvent actionEvent)  {
        if (songListView.getSelectionModel().getSelectedItem() != null) {
            setSongMetadata(SongsManager.songs.get(getSelectedIndex()).getSongMetadata());

            SongsManager.songs.get(actualSongIndex).stop();
            actualSongIndex = getSelectedIndex();
            SongsManager.playActualSong(actualSongIndex);

            prepareProgressSlider();
            // TODO: hacer que cuando la cancion termine, vuelva a hacer esto
            SongsManager.songs.get(actualSongIndex).getMediaPlayer()
                    .setOnEndOfMedia(() -> {
                        SongsManager.playNextSong();
                        setSongMetadata(SongsManager.songs.get(actualSongIndex).getSongMetadata());
                        removeSliderListeners();
                        setSliderListeners();
                    });

            setNextSongListener();

            setSliderListeners();
        }
    }
    // END OF LEFT

    private int getSelectedIndex() {
        return songListView.getSelectionModel().getSelectedIndex();
    }

    private void setSliderListeners() {
        volumeListener = observable -> {
            if (SongsManager.songs.get(actualSongIndex) != null) {
                SongsManager.songs.get(actualSongIndex).setVolume(volumeSlider.getValue() / 100);
            }
        };
        progressListener = observable -> {
            if (progressSlider.isValueChanging()) {
                SongsManager.songs.get(actualSongIndex).setSeek(progressSlider.getValue());
            }
        };

        volumeSlider.valueProperty().addListener(volumeListener);
        progressSlider.valueProperty().addListener(progressListener);
    }

    private void removeSliderListeners() {
        volumeSlider.valueProperty().removeListener(volumeListener);
        progressSlider.valueProperty().removeListener(progressListener);
    }

    private void setNextSongListener() {
        DISCORD_MANAGER.setDiscordActivity(
                SongsManager.getActualSongMetadata().getTitle(),
                SongsManager.getActualSongMetadata().getArtist(),
                SongsManager.getActualSongMetadata().getCoverArtAsString(),
                SongsManager.getActualSongMetadata().getDuration()
        );

        System.out.println(SongsManager.getActualSongMetadata().getCoverArtAsString());

        SongsManager.songs.get(actualSongIndex).getMediaPlayer().setOnEndOfMedia(() -> {
            prepareNextSong();
            setNextSongListener();});
    }

    private void prepareProgressSlider() {
        progressSlider.setMax(SongsManager.songs.get(actualSongIndex).getMediaPlayer().getTotalDuration().toSeconds());
        SongsManager.songs.get(actualSongIndex).getMediaPlayer().currentTimeProperty()
                .addListener((observableValue, duration, t1) -> {
                            if (!progressSlider.isValueChanging()) {
                                progressSlider.setValue(t1.toSeconds());
                            }
                            elapsedTimeLabel.setText(formatDuration(t1.toMillis()));
                        }
                );
    }

    private void prepareNextSong() {
        SongsManager.playNextSong();
        setSongMetadata(SongsManager.songs.get(actualSongIndex).getSongMetadata());
        removeSliderListeners();
        setSliderListeners();
        prepareProgressSlider();
    }
}
