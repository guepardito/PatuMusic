package es.guepardito.patumusic.view;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;

import java.io.IOException;

public class IndexController {
    public BorderPane rootPane;
    public IndexCenterController indexCenterController;

    @FXML
    public void initialize() {

    }

    public void setLeftPane(String fxmlPath) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Parent newLeftPane = loader.load();
            rootPane.setLeft(newLeftPane);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
