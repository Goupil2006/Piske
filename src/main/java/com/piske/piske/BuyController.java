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

public class BuyController implements Initializable {

    private StationController stationController;
    private InterfaceController interfaceController;
    // private BuyController buyController;
    private GameController gameController;
    private UpgradeController upgradeController;

    public void setContollers(StationController stationController, InterfaceController interfaceController,
            GameController gameController, UpgradeController upgradeController) {
        this.stationController = stationController;
        this.interfaceController = interfaceController;
        // this.buyController = buyController;
        this.gameController = gameController;
        this.upgradeController = upgradeController;
    }

    private Consumer<String> createStation;

    @FXML
    public AnchorPane buyhead;

    private Parent root;

    @FXML
    public void start(Consumer<String> createStation) throws Exception {
        System.out.println("show buy");
        buyhead.setVisible(true);
        this.createStation = createStation;
    }

    @FXML
    public void stop() throws Exception {
        System.out.println("hide buy");
        buyhead.setVisible(false);
    }

    @FXML
    public void addsilli() throws Exception {
        this.stop();
        this.createStation.accept("silli");
    }

    @FXML
    public void addIra() throws Exception {
        this.stop();
        this.createStation.accept("ira");
    }

    @FXML
    public void addBiene() throws Exception {
        this.stop();
        this.createStation.accept("biene");
    }

    @FXML
    public void addConny() throws Exception {
        this.stop();
        this.createStation.accept("conny");
    }

    @FXML
    public void addEvy() throws Exception {
        this.stop();
        this.createStation.accept("evy");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buyhead.setVisible(false);
        System.out.println("Buycontoller initialized");
    }
}
