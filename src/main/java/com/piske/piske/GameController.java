package com.piske.piske;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private AnchorPane gamescreen;

    @FXML
    private Button addStation1;

    @FXML
    private void addStation1(ActionEvent event) throws Exception {
        System.out.println("Station 1 added");
        createImageView(20, 20);
    }

    private void createImageView(double x, double y) {
        ImageView imageView = new ImageView();
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        Image image = new Image(getClass().getResourceAsStream("weg.png"));
        imageView.setImage(image);
        gamescreen.getChildren().add(imageView);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}