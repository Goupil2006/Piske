package com.piske.piske;

import com.almasb.fxgl.ui.ProgressBar;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
    public Button startwavebutton;

    @FXML
    public Button done;

    public Uhr uhr;

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

    @FXML
    public Label waveindicator;

    private float amount = 100;

    public void setWaveIndicator(int wave, int maxWave) {
        waveindicator.setText("Welle: " + wave + " / " + maxWave);
    }

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
        startwavebutton.setOnAction(event -> {
            try {
                gameController.startwave(event);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        this.uhr = new Uhr(uhrzeiger);
        done.setOnAction(event -> {
            System.out.println("Back");
            this.stationController.active = false;
            FXMLLoader loader1 = new FXMLLoader(getClass().getResource("/com/piske/piske/mainmenu.fxml"));
            Parent root1 = null;
            try {
                root1 = loader1.load();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            MainMenuController mainMenuController = loader1.getController();
            System.out.println("mainMenuController loaded: " + mainMenuController);

            // Add both roots to a main container (e.g., VBox)
            VBox root = new VBox(root1);

            Scene scene = new Scene(root, 1280, 720);
            Stage stage = (Stage) this.gameController.gamescreen.getScene().getWindow();
            Platform.runLater(() -> {
                stage.setScene(scene);
                stage.show();
            });
        });
    }
}
