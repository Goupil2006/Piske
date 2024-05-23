package com.piske.piske;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class BuyController implements Initializable {

    @FXML
    public AnchorPane buyhead;

    public void show() throws Exception {
        System.out.println("setvisible");
        buyhead.setVisible(false);
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
