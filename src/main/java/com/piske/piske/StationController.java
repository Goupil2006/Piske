package com.piske.piske;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StationController implements Initializable {

    @FXML
    private AnchorPane plane;

    @FXML
    private ImageView station;

    private Parent root;

    @FXML
    private void addStation(MouseEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/piske/piske/Interface.fxml"));
        root = loader.load();

        InterfaceController interfaceController = loader.getController();
        interfaceController.test(() -> {
            try {
                addSilli();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });


    }

    private void addSilli() throws IOException {
        ImageView imageView = new ImageView();
        imageView.setLayoutX(station.getLayoutX());
        imageView.setLayoutY(station.getLayoutY());
        Image image = new Image(getClass().getResourceAsStream("/com/piske/piske/Images/Station.png"));
        imageView.setImage(image);
        plane.getChildren().add(imageView);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/piske/piske/game.fxml"));
        root = loader.load();

        GameController gameController = loader.getController();
        gameController.createProjectile(1, 1, 0,0,0,0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
