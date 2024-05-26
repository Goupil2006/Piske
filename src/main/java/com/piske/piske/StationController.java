package com.piske.piske;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import java.util.function.Consumer;


import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StationController implements Initializable {

    // private StationController stationController;
    private InterfaceController interfaceController;
    private BuyController buyController;
    private GameController gameController;

    public void setContollers(InterfaceController interfaceController, GameController gameController,
            BuyController buyController) {
        // this.stationController = stationController;
        this.interfaceController = interfaceController;
        this.buyController = buyController;
        this.gameController = gameController;
    }

    @FXML
    private AnchorPane plane;

    @FXML
    private ImageView station;

    private Parent root;

    @FXML
    private void addStation(MouseEvent event) throws Exception {
        System.out.println(this.buyController);
        Consumer<String> createStation = ((String type) -> {
            switch (type) {
                case "silli":
                    try {
                        addSilli();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    break;
            }
        });
        this.buyController.start(createStation);
    }

    private void addSilli() throws IOException {
        ImageView imageView = new ImageView();
        imageView.setLayoutX(station.getLayoutX());
        imageView.setLayoutY(station.getLayoutY());
        Image image = new Image(getClass().getResourceAsStream("/com/piske/piske/Images/Station.png"));
        imageView.setImage(image);
        plane.getChildren().add(imageView);

        this.gameController.createProjectile(1, 1, 0, 0, 0, 0);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}
