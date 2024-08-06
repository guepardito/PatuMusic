package es.guepardito.patumusic.view;

import javafx.event.ActionEvent;
import javafx.scene.control.ListView;

public class IndexLeftPlaylistsController {
    public ListView<String> playlistListView;

    public void handleAddPlaylist(ActionEvent actionEvent) {
        playlistListView.getItems().add("Nueva Playlist");
    }

    public void handleRemovePlaylist(ActionEvent actionEvent) {
    }
}
