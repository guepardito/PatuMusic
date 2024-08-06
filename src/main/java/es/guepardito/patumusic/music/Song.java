package es.guepardito.patumusic.music;

public class Song {
    private SongMetadata songMetadata;
    private SongPlayer songPlayer;

    public Song(String absPath) {
        songMetadata = new SongMetadata(absPath);
    }

    public SongPlayer getSongPlayer() {
        return songPlayer;
    }

    public void setSongPlayer(SongPlayer songPlayer) {
        this.songPlayer = songPlayer;
    }

    public SongMetadata getSongMetadata() {
        return songMetadata;
    }

    public void setSongMetadata(SongMetadata songMetadata) {
        this.songMetadata = songMetadata;
    }

    @Override
    public String toString() {
        return songMetadata.getTitle();
    }
}
