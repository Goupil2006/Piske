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

    public void setContollers(StationController stationController, InterfaceController interfaceController,
                              GameController gameController) {
        this.stationController = stationController;
        this.interfaceController = interfaceController;
        //this.buyController = buyController;
        this.gameController = gameController;
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
    public void addsilli() {
        this.createStation.accept("silli");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buyhead.setVisible(false);
        System.out.println("Buycontoller initialized");
    }
}
