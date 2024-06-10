package com.piske.piske;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class BuyController implements Initializable {

    private StationController stationController;
    private InterfaceController interfaceController;
    // private BuyController buyController;
    private GameController gameController;
    private UpgradeController upgradeController;

    // set Controllers
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

    @FXML
    public Button silli;

    @FXML
    public Button ira;

    @FXML
    public Button biene;

    @FXML
    public Button conny;

    @FXML
    public Button evy;

    private Parent root;

    // start buying
    @FXML
    public void start(Consumer<String> createStation) throws Exception {
        buyhead.setVisible(true);
        // asign function to varibale for use elsewhere
        this.createStation = createStation;
    }

    // stop buying
    @FXML
    public void stop() throws Exception {
        buyhead.setVisible(false);
        // asign function to varibale for use elsewhere
        this.upgradeController.start();
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

    public int getStand() {
        try {
            String stand = Files.readString(Paths.get(new File("Stand.txt").getAbsolutePath()));
            if (stand.equals("")) {
                return 0;
            } else {
                return Integer.parseInt(stand);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buyhead.setVisible(false);
        switch (getStand()) {
            case 0:
                ira.setDisable(true);
                biene.setDisable(true);
                conny.setDisable(true);
                evy.setDisable(true);
                break;
            case 1:
                biene.setDisable(true);
                conny.setDisable(true);
                evy.setDisable(true);
                break;

            case 2:
                conny.setDisable(true);
                evy.setDisable(true);
                break;
            case 3:
                evy.setDisable(true);
                break;
            case 4:
                break;
            default:
                break;
        }
    }
}
