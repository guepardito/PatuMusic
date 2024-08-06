package es.guepardito.patumusic.music;

import com.mpatric.mp3agic.ID3v1;
import com.mpatric.mp3agic.ID3v2;
import com.mpatric.mp3agic.InvalidDataException;
import com.mpatric.mp3agic.Mp3File;
import com.mpatric.mp3agic.UnsupportedTagException;

import java.io.FileOutputStream;
import java.io.IOException;

public class SongMetadata {
    private String title;
    private String artist;
    private String album;
    private long duration;
    private byte[] coverArt;

    public SongMetadata(String filePath) {
        try {
            Mp3File mp3File = new Mp3File(filePath);
            if (mp3File.hasId3v2Tag()) {
                ID3v2 id3v2Tag = mp3File.getId3v2Tag();
                this.title = id3v2Tag.getTitle();
                this.artist = id3v2Tag.getArtist();
                this.album = id3v2Tag.getAlbum();
                this.coverArt = id3v2Tag.getAlbumImage();
            } else if (mp3File.hasId3v1Tag()) {
                ID3v1 id3v1Tag = mp3File.getId3v1Tag();
                this.title = id3v1Tag.getTitle();
                this.artist = id3v1Tag.getArtist();
                this.album = id3v1Tag.getAlbum();
                // ID3v1 no soporta imágenes
            }
            this.duration = mp3File.getLengthInMilliseconds();
        } catch (IOException | UnsupportedTagException | InvalidDataException e) {
            e.printStackTrace();
        }
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public long getDuration() {
        return duration;
    }

    public byte[] getCoverArt() {
        return coverArt;
    }

    @Override
    public String toString() {
        return "SongMetadata{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", album='" + album + '\'' +
                '}';
    }
}
