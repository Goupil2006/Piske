package com.piske.piske;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // FXMLLoader menu = new
        // FXMLLoader(Main.class.getResource("/com/piske/piske/mainmenu.fxml"));

        // Load MainMenuController
        FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/com/piske/piske/mainmenu.fxml"));
        Parent root1 = loader1.load();
        MainMenuController mainMenuController = loader1.getController();
        System.out.println("mainMenuController loaded: " + mainMenuController);

        // Add both roots to a main container
        VBox root = new VBox(root1);

        // Create a Scene
        Scene scene = new Scene(root, 1280, 720);

        // Scene scene = new Scene(menu.load(), 1280, 720);
        stage.setTitle("Piske: Feed the Beasts!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}