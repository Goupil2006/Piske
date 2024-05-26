package com.piske.piske;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
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

    public void setContollers(InterfaceController interfaceController, GameController gameController,
            BuyController buyController, StationController stationController, Parent root2, Parent root3, Parent root4,
            Parent root5) {
        this.stationController = stationController;
        this.interfaceController = interfaceController;
        this.buyController = buyController;
        // this.mainMenuController = mainMenuController;
        this.gameController = gameController;
        this.root2 = root2;
        this.root3 = root3;
        this.root4 = root4;
        this.root5 = root5;
    }

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
        GameController gameContoller = loader5.getController();
        System.out.println("gameContoller loaded: " + gameContoller);

        interfaceController.setContollers(stationController, gameContoller, buyController);
        stationController.setContollers(interfaceController, gameContoller, buyController);
        buyController.setContollers(stationController, interfaceController, gameContoller);
        gameContoller.setContollers(stationController, interfaceController, buyController);

        AnchorPane root = new AnchorPane(root5, root4, root3, root2);

        // Create a Scene
        Scene scene = new Scene(root, 1280, 720);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
