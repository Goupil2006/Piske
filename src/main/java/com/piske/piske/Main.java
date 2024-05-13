package com.piske.piske;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader menu = new FXMLLoader(Main.class.getResource("mainmenu.fxml"));
        Scene scene = new Scene(menu.load(), 1280, 720);
        stage.setTitle("Piske: Feed the Beasts!");
        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {
        launch();

    }
}