package es.guepardito.patumusic.music;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.io.File;

public class Song {
    private SongMetadata songMetadata;
    private MediaPlayer mediaPlayer;
    private Media songMedia;

    public Song(String absPath) {
        songMetadata = new SongMetadata(absPath);
        songMedia = new Media(new File(absPath).toURI().toString());
        mediaPlayer = new MediaPlayer(songMedia);
        mediaPlayer.setVolume(0.04);
    }

    public void play() {
        mediaPlayer.play();
        SongsManager.isPlaying = true;
    }

    // deletes mediaPlayer
    public void stop() {
        mediaPlayer.stop();
        SongsManager.isPlaying = false;
    }

    // pause the song so it can be resumed
    public void pause() {
        mediaPlayer.pause();
        SongsManager.isPlaying = false;
    }

    public void restart() {
        mediaPlayer.stop();
        mediaPlayer.play();
        SongsManager.isPlaying = true;
    }

    public double getVolume() {
        return mediaPlayer.getVolume();
    }

    public void setVolume(double volume) {
        mediaPlayer.setVolume(volume);
    }

    public void setSeek(double duration) {
        mediaPlayer.seek(Duration.seconds(duration));
    }

    public SongMetadata getSongMetadata() {
        return songMetadata;
    }

    public void setSongMetadata(SongMetadata songMetadata) {
        this.songMetadata = songMetadata;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    @Override
    public String toString() {
        return songMetadata.getTitle();
    }
}
