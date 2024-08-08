module es.guepardito.patumusic {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires mp3agic;
    requires javafx.media;
    requires static lombok;
    requires com.sun.jna;
    requires slf4j.api;
    requires java.desktop;

    opens es.guepardito.patumusic to javafx.fxml;
    exports es.guepardito.patumusic;
    exports es.guepardito.patumusic.view;
    opens es.guepardito.patumusic.view to javafx.fxml;
    exports es.guepardito.patumusic.music;
    opens es.guepardito.patumusic.music to javafx.fxml;
    opens es.guepardito.patumusic.discord.jna;
    exports es.guepardito.patumusic.discord.jna to com.sun.jna;
}