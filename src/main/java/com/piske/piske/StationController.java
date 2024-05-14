package com.piske.piske;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class StationController implements Initializable {

    @FXML
    private AnchorPane plane;

    @FXML
    private ImageView station;

    @FXML
    private void addStation(MouseEvent event) throws Exception {
        ImageView imageView = new ImageView();
        imageView.setLayoutX(station.getLayoutX());
        imageView.setLayoutY(station.getLayoutY());
        Image image = new Image(getClass().getResourceAsStream("Station.png"));
        imageView.setImage(image);
        plane.getChildren().add(imageView);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
