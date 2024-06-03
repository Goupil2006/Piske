package com.piske.piske;

import com.almasb.fxgl.ui.ProgressBar;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class InterfaceController implements Initializable {

    private StationController stationController;
    // private InterfaceController interfaceController;
    private BuyController buyController;
    private GameController gameController;
    private UpgradeController upgradeController;

    @FXML
    private Parent root;

    @FXML
    private AnchorPane buyinclude;

    @FXML
    private ImageView uhrzeiger;

    @FXML
    private Button startwavebutton;

    public Uhr uhr = new Uhr(uhrzeiger);


    public void setContollers(StationController stationController, GameController gameController,
            BuyController buyController, UpgradeController upgradeController) {
        this.stationController = stationController;
        // this.interfaceController = interfaceController;
        this.buyController = buyController;
        this.gameController = gameController;
        this.upgradeController = upgradeController;
    }

    @FXML
    public AnchorPane rootinterface;

    @FXML
    public Text money;

    private float amount = 2000;

    public void changeAmount(float amount) {
        this.amount += amount;
        money.setText(this.amount + "$");
    }

    public float getMoney() {
        return amount;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("InterfaceController initialized");
        money.setText(amount + "$");
        startwavebutton.setOnAction(event -> gameController.startwave(event));
    }
}
