package com.piske.piske;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;

public class GameController implements Initializable {

    @FXML
    private AnchorPane gamescreen;

    Weg schuelerweg = new Weg();

    @FXML
    private void addStation1(ActionEvent event) throws Exception {
        System.out.println("Station 1 added");
        createStraightPathView(22, 22, true);
    }

    public void createStraightPathView(int x, int y, boolean r) {
        x = x * 72;
        y = y * 72;
        y += 21;
        ImageView imageView = new ImageView();
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        if (r) {
            imageView.setRotate(90);
        } else {
            imageView.setRotate(0);
        }
        Image image = new Image(getClass().getResourceAsStream("weg.png"));
        imageView.setImage(image);
        gamescreen.getChildren().add(imageView);
    }

    public void createCurvePathView(int x, int y, int r) {
        x = x * 72;
        y = y * 72;

        ImageView imageView = new ImageView();
        if (r == 0) {
            imageView.setRotate(0);
            x += 21;
        } else if (r == 1) {
            imageView.setRotate(90);
            y += 21;
            x += 21;
        } else if (r == 2) {
            imageView.setRotate(180);
            y += 21;
        } else if (r == 3) {
            imageView.setRotate(270);
        }
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        Image image = new Image(getClass().getResourceAsStream("wegL.png"));
        imageView.setImage(image);
        gamescreen.getChildren().add(imageView);
    }

    public void renderWeg(Weg w) {
        Pfad temp = null;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createStraightPathView(1, 4, false);
        createCurvePathView(2, 4, 3);
        createStraightPathView(2, 3, true);
        createCurvePathView(2, 2, 1);
        createStraightPathView(3, 2, false);
        createStraightPathView(4, 2, false);
        createStraightPathView(5, 2, false);
        createStraightPathView(6, 2, false);
        createStraightPathView(7, 2, false);
        createStraightPathView(8, 2, false);
        createStraightPathView(9, 2, false);
        createCurvePathView(10, 2, 2);
        createStraightPathView(10, 3, true);
        createStraightPathView(10, 4, true);
        createStraightPathView(10, 5, true);
        createStraightPathView(10, 6, true);
        createCurvePathView(10, 7, 3);
        createStraightPathView(9, 7, false);
        createStraightPathView(8, 7, false);
        createStraightPathView(7, 7, false);
        createStraightPathView(6, 7, false);
        createStraightPathView(5, 7, false);
        createStraightPathView(4, 7, false);
        createStraightPathView(3, 7, false);
        createCurvePathView(2, 7, 0);
        createStraightPathView(2, 6, true);
        createCurvePathView(2, 5, 2);
        createStraightPathView(1, 5, false);
        createStraightPathView(0, 5, false);
    }
}