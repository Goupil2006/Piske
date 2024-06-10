package com.piske.piske;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
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

    // set Controllers
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

    private int moneyspent(Station station) {
        int moneyspent = 0;
        for (int i = 0; i < station.getDamage() / station.originaldamage; i++) {
            moneyspent += station.originaldamage * i * 5;
        }
        return moneyspent;
    }

    private void rerender() {
        int foo = 0;
        int i = 0;
        while (foo < numberStations) {
            if (deleteButtons[foo] == null) {
                deleteButtons[foo] = deleteButtons[foo + 1];
                stationNames[foo] = stationNames[foo + 1];
                upgradeButtons[foo] = upgradeButtons[foo + 1];
            }
            if (deleteButtons[foo] != null) {
                int finalFoo = foo;
                int finalI = i;
                Platform.runLater(() -> {
                    deleteButtons[finalFoo].setLayoutY(18 + finalI * 29);
                    stationNames[finalFoo].setLayoutY(18 + finalI * 29);
                    upgradeButtons[finalFoo].setLayoutY(18 + finalI * 29);
                });
            }
            foo++;
        }
    }

    Button[] deleteButtons = new Button[100];
    Label[] stationNames = new Label[100];
    Button[] upgradeButtons = new Button[100];

    @FXML
    public void addStation(Station station) throws Exception {
        // Code to add a delete button for the station
        int temp = numberStations;
        deleteButtons[temp] = new Button("Löschen");
        stationNames[temp] = new Label(station.getName());
        upgradeButtons[temp] = new Button("Upgrade " + station.getDamage() * 5 + "$");
        deleteButtons[temp].setOnAction(event -> {
            // Remove the station from the station controller
            stationController.removeStation(station);

            // Refund the money spent on the station
            int refundAmount = station.price + moneyspent(station);
            this.interfaceController.changeAmount(refundAmount);

            // Remove the station from the interface
            upgreadheadpane.getChildren().removeAll(deleteButtons[temp], stationNames[temp], upgradeButtons[temp]);
            deleteButtons[temp] = null;
            this.rerender();
            numberStations--;
        });
        deleteButtons[temp].setLayoutX(254 - 80);
        deleteButtons[temp].setLayoutY(18 + temp * 29);
        deleteButtons[temp].setMnemonicParsing(false);
        deleteButtons[temp].setStyle("-fx-background-color: FF5964; -fx-background-radius: 10;");

        // Add the delete button to the anchor pane
        upgreadheadpane.getChildren().add(deleteButtons[temp]);

        stationNames[temp].setText(station.getName());

        upgradeButtons[temp].setOnAction(event -> {
            // if (this.gameController.phase % 2 != 0) {
            station.upgrade();
            upgradeButtons[temp].setText("Upgrade " + station.getDamage() * 5 + "$");
            // }
        });

        stationNames[temp].setLayoutX(36 - 10);
        stationNames[temp].setLayoutY(18 + temp * 29);

        upgradeButtons[temp].setLayoutX(254 - 10);
        upgradeButtons[temp].setLayoutY(18 + temp * 29);
        upgradeButtons[temp].setMnemonicParsing(false);
        upgradeButtons[temp].setStyle("-fx-background-color: FF5964; -fx-background-radius: 10;");

        // Add the upgrade button to the anchor pane
        upgreadheadpane.getChildren().add(upgradeButtons[temp]);
        upgreadheadpane.getChildren().add(stationNames[temp]);
        upgreadheadpane.setPrefHeight(50 * temp);
        numberStations += 1;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        upgreadheadpane.setVisible(false);
        System.out.println("UpgradeController initialized");
    }
}
