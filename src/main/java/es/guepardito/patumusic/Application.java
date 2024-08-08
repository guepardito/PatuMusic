package es.guepardito.patumusic;

import es.guepardito.patumusic.discord.DiscordActivityController;
import es.guepardito.patumusic.music.SongsManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    public static DiscordActivityController DISCORD_MANAGER;
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Application.class.getResource("index-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("PatuMusic");
        stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(event -> {
            DISCORD_MANAGER.delete();
        });
    }

    public static void main(String[] args) {
        System.out.println(System.getProperty("user.dir"));
        DISCORD_MANAGER = new DiscordActivityController();
        DISCORD_MANAGER.start();
        SongsManager.initialize();
        launch();
    }
}