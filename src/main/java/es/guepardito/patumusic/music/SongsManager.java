package es.guepardito.patumusic.music;

import java.io.File;
import java.util.ArrayList;
import java.util.function.Function;

public class SongsManager {
    public static ArrayList<Song> songs;
    public static int actualSongIndex;
    public static boolean isPlaying;

    public static void initialize() {
        songs = new ArrayList<>();
        actualSongIndex = 0;
        isPlaying = false;
    }

    public static void playActualSong(int newIndex) {
        if (isPlaying) {
            if (actualSongIndex != newIndex) {
                songs.get(actualSongIndex).stop();
            } else {
                songs.get(actualSongIndex).pause();
            }
        } else {
            actualSongIndex = newIndex;
            songs.get(actualSongIndex).play();
        }
    }

    public static void playNextSong() {
        songs.get(actualSongIndex).stop();
        actualSongIndex = (actualSongIndex +1) % songs.size();
        songs.get(actualSongIndex).play();
    }
}
