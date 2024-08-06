module es.guepardito.patumusic {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires mp3agic;

    opens es.guepardito.patumusic to javafx.fxml;
    exports es.guepardito.patumusic;
    exports es.guepardito.patumusic.view;
    opens es.guepardito.patumusic.view to javafx.fxml;
    exports es.guepardito.patumusic.music;
    opens es.guepardito.patumusic.music to javafx.fxml;
}