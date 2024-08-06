package es.guepardito.patumusic.view;

import es.guepardito.patumusic.music.SongMetadata;
import javafx.scene.image.Image;

import java.io.ByteArrayInputStream;
import java.util.concurrent.TimeUnit;


public class AppViewController {
//    public static void setSongMetadata(SongMetadata metadata) {
//        titleLabel.setText(metadata.getTitle());
//        artistLabel.setText(metadata.getArtist());
//        albumLabel.setText(metadata.getAlbum());
//        durationLabel.setText(formatDuration(metadata.getDuration()));
//        progressBar.setProgress(0);
//
//        byte[] coverArt = metadata.getCoverArt();
//        if (coverArt != null) {
//            Image image = new Image(new ByteArrayInputStream(coverArt));
//            coverArtImageView.setImage(image);
//        } else {
//            coverArtImageView.setImage(null);
//        }
//    }

    public static String formatDuration(long millis) {
        return String.format(
                "%02d:%02d",
                TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis))
        );

    }
}
