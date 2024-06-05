package com.piske.piske;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class UpgradeController implements Initializable {

    private StationController stationController;
    private InterfaceController interfaceController;
    private BuyController buyController;
    private GameController gameController;

    public void setContollers(StationController stationController, InterfaceController interfaceController,
            GameController gameController, BuyController buyController) {
        this.stationController = stationController;
        this.interfaceController = interfaceController;
        this.buyController = buyController;
        this.gameController = gameController;
    }

    @FXML
    public AnchorPane upgreadheadpane;

    private int numberStations = 0;

    @FXML
    public void start() throws Exception {
        upgreadheadpane.setVisible(true);
    }

    @FXML
    public void stop() throws Exception {
        upgreadheadpane.setVisible(false);
    }

    @FXML
    public void addStation(Station station) throws Exception {
        numberStations += 1;
        Label stationName = new Label(station.getName());
        stationName.setText(station.getName());
        Button upgradeButton = new Button("Upgrade " + station.getDamage() * 5 + "$");
        upgradeButton.setOnAction(event -> {
            if (this.gameController.phase % 2 != 0) {
                station.upgrade();
                upgradeButton.setText("Upgrade " + station.getDamage() * 5 + "$");
            }
        });

        stationName.setLayoutX(36 - 10);
        stationName.setLayoutY(15 + numberStations * 29 - 22.5);

        upgradeButton.setLayoutX(254 - 10);
        upgradeButton.setLayoutY(11 + numberStations * 29 - 22.5);
        upgradeButton.setMnemonicParsing(false);
        upgradeButton.setStyle("-fx-background-color: FF5964; -fx-background-radius: 10;");

        // Add the upgrade button to the anchor pane
        upgreadheadpane.getChildren().add(upgradeButton);
        upgreadheadpane.getChildren().add(stationName);
        upgreadheadpane.setPrefHeight(50 * numberStations);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        upgreadheadpane.setVisible(false);
        System.out.println("UpgradeController initialized");
    }
}
