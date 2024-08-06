package es.guepardito.patumusic.view;

import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;

public class IndexTopController {
    private IndexController indexController;

    @FXML
    private MenuItem openPlaylists;

    @FXML
    private MenuItem openSongs;

    public void setIndexController(IndexController indexController) {
        this.indexController = indexController;
    }

    @FXML
    private void openPlaylists() {
        if (indexController != null) {
            indexController.setLeftPane("index-left-playlists.fxml");
            System.out.println("hola");
        }
    }

    @FXML
    private void openSongs() {
        if (indexController != null) {
            indexController.setLeftPane("index-left-songs.fxml");
        }
    }
}
