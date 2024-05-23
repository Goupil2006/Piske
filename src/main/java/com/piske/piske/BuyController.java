package com.piske.piske;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class BuyController implements Initializable {

    @FXML
    public AnchorPane buyhead;

    private Parent root;

    public void show() throws Exception {
        System.out.println("setvisible");

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/piske/piske/game.fxml"));
        root = loader.load();

        GameController gameController = loader.getController();
        gameController.update();
    }

    public void add(ActionEvent event) throws Exception {
        System.out.println("test2");
        //
        //
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buyhead.setVisible(true);
    }
}
