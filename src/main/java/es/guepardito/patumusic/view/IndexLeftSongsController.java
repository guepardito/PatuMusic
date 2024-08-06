package es.guepardito.patumusic.view;

import es.guepardito.patumusic.music.Song;
import es.guepardito.patumusic.music.SongsManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.ArrayList;

public class IndexLeftSongsController {
    private ArrayList<Song> songs;
    public ListView<String> songListView;
    public Button addSong;
    public Button deleteSong;

    private IndexCenterController indexCenterController;

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
                System.out.println(song);
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

    public void handlePlaySongs(ActionEvent actionEvent)  { // TODO: hacer que pueda modificar el indexCenterController
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("index-center.fxml"));
        indexCenterController = fxmlLoader.getController();
        indexCenterController.setSongMetadata(SongsManager.songs.get(0).getSongMetadata());
    }
}
