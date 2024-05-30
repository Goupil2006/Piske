package com.piske.piske;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

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
    public AnchorPane upgreadhead;

    private Parent root;

    @FXML
    public void start() throws Exception {
        upgreadhead.setVisible(true);
    }

    @FXML
    public void stop() throws Exception {
        upgreadhead.setVisible(false);
    }

    @FXML
    public void addStation(Station station) throws Exception {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        upgreadhead.setVisible(false);
        System.out.println("UpgradeController initialized");
    }
}
