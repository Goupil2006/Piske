package com.piske.piske;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ResourceBundle;

public class StationController implements Initializable {

    @FXML
    private ImageView station;

    @FXML
    private void addStation(MouseEvent event) throws Exception {
        System.out.println("adddddd");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println("test");
    }
}
