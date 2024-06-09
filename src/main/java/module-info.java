module com.piske.piske {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires org.json;
    requires javafx.media;
    requires com.fasterxml.jackson.databind;
    requires com.jcraft.jsch;

    opens com.piske.piske to javafx.fxml;

    exports com.piske.piske;
}