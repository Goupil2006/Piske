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

    Weg schuelerweg = new Weg();


    @FXML
    private void addStation1(ActionEvent event) throws Exception {
        System.out.println("Station 1 added");
        createImageView(20, 20);
    }

    public void createImageView(int x, int y) {
        x = x * 72;
        y = y * 72;
        y += 21;
        ImageView imageView = new ImageView();
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        Image image = new Image(getClass().getResourceAsStream("weg.png"));
        imageView.setImage(image);
        gamescreen.getChildren().add(imageView);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createImageView(1,4);
        createImageView(2,4);
        createImageView(2,3);
        createImageView(2,2);
        createImageView(3,2);
        createImageView(4,2);
        createImageView(5,2);
        createImageView(6,2);
        createImageView(7,2);
        createImageView(8,2);
        createImageView(9,2);
        createImageView(10,2);
        createImageView(10,3);
        createImageView(10,4);
        createImageView(10,5);
        createImageView(10,6);
        createImageView(10,7);
        createImageView(9,7);
        createImageView(8,7);
        createImageView(7,7);
        createImageView(6,7);
        createImageView(5,7);
        createImageView(4,7);
        createImageView(3,7);
        createImageView(2,7);
        createImageView(2,6);
        createImageView(2,5);
        createImageView(1,5);
        createImageView(0,5);
    }
}