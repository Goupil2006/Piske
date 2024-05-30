package com.piske.piske;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class MainMenuController implements Initializable {

    private StationController stationController;
    private InterfaceController interfaceController;
    private BuyController buyController;
    // private MainMenuController mainMenuController;
    private GameController gameController;
    private Parent root2;
    private Parent root3;
    private Parent root4;
    private Parent root5;

    @FXML
    private Slider difficulty;

    @FXML
    private Slider sound;

    private GameController gameContoller;

    int difficultyValue = 1;
    int soundValue = 1;

    @FXML
    private void gameStart(ActionEvent event) throws Exception {
        // Load StationController
        FXMLLoader loader2 = new FXMLLoader(getClass().getResource("/com/piske/piske/Station.fxml"));
        Parent root2 = loader2.load();
        StationController stationController = loader2.getController();
        System.out.println("stationController loaded: " + stationController);

        // Load BuyController
        FXMLLoader loader3 = new FXMLLoader(getClass().getResource("/com/piske/piske/Buy.fxml"));
        Parent root3 = loader3.load();
        BuyController buyController = loader3.getController();
        System.out.println("buyController loaded: " + buyController);

        // Load InterfaceContoller
        FXMLLoader loader4 = new FXMLLoader(getClass().getResource("/com/piske/piske/Interface.fxml"));
        Parent root4 = loader4.load();
        InterfaceController interfaceController = loader4.getController();
        System.out.println("interfaceController loaded: " + interfaceController);

        // Load GameController
        FXMLLoader loader5 = new FXMLLoader(getClass().getResource("/com/piske/piske/game.fxml"));
        Parent root5 = loader5.load();
        gameContoller = loader5.getController();
        System.out.println("gameContoller loaded: " + gameContoller);

        // Load UpgradeController
        FXMLLoader loader6 = new FXMLLoader(getClass().getResource("/com/piske/piske/Upgrade.fxml"));
        Parent root6 = loader6.load();
        UpgradeController upgradeController = loader6.getController();
        System.out.println("UpgradeContoller loaded: " + upgradeController);

        interfaceController.setContollers(stationController, gameContoller, buyController, upgradeController);
        stationController.setContollers(interfaceController, gameContoller, buyController, upgradeController);
        buyController.setContollers(stationController, interfaceController, gameContoller, upgradeController);
        gameContoller.setContollers(stationController, interfaceController, buyController, upgradeController);
        upgradeController.setContollers(stationController, interfaceController, gameContoller, buyController);
        this.gameContoller.setDifAndSound(difficultyValue, soundValue);

        AnchorPane root = new AnchorPane(root5, root4, root3, root2, root6);

        // Create a Scene
        Scene scene = new Scene(root, 1280, 720);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        difficulty.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.difficultyValue = newValue.intValue();
        });
        sound.valueProperty().addListener((observable, oldValue, newValue) -> {
            this.soundValue = newValue.intValue();
        });
    }
}
