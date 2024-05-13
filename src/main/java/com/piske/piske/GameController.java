package com.piske.piske;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private Button addStation1;

    @FXML
    private void addStation1(ActionEvent event) throws Exception {
        System.out.println("Station 1 added");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}