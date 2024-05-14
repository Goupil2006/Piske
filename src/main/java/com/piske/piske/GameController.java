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

    @FXML
    private Button addStation1;

    Weg schuelerweg = new Weg();


    @FXML
    private void addStation1(ActionEvent event) throws Exception {
        System.out.println("Station 1 added");
    }

    public void createStraightPathView(int x, int y, boolean r) {
        x = x * 72;
        y = y * 72;
        y += 21;
        ImageView imageView = new ImageView();
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        if(r) {
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
        if(r == 0 ) {
            imageView.setRotate(0);
            x += 21;
        } else if (r == 1){
            imageView.setRotate(90);
            y += 21;
            x += 21;
        } else if (r == 2) {
            imageView.setRotate(180);
            y += 21;
        } else if (r == 3){
            imageView.setRotate(270);
        }
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        Image image = new Image(getClass().getResourceAsStream("wegL.png"));
        imageView.setImage(image);
        gamescreen.getChildren().add(imageView);
    }

    public void renderWeg(Weg w) {
        Pfad temp = Weg.getHead();
        while(Weg.getTail() != temp) {
            /*if (temp.getStart() == 'n' && temp.getEnd() == 's' || temp.getStart() == 's' && temp.getEnd() == 'n') {
                createStraightPathView(temp.getMapX(), temp.getMapY(), true);
            } else if (temp.getStart() == 'e' && temp.getStart() == 'w' || temp.getStart() == 'w' && temp.getStart() == 'e' ){
                createStraightPathView(temp.getMapX(), temp.getMapY(), false);
            } else if (temp.getStart() == 'n' && temp.getStart() == 'e' || temp.getStart() == 'e' && temp.getStart() == 'n' ) {
                createCurvePathView(temp.getMapX(), temp.getMapY(), 0);
            } else if (temp.getStart() == 's' && temp.getStart() == 'e' || temp.getStart() == 'e' && temp.getStart() == 's' ) {
                createCurvePathView(temp.getMapX(), temp.getMapY(), 1);
            } else if (temp.getStart() == 's' && temp.getStart() == 'w' || temp.getStart() == 'w' && temp.getStart() == 's' ) {
                createCurvePathView(temp.getMapX(), temp.getMapY(), 2);
            } else if (temp.getStart() == 'n' && temp.getStart() == 'w' || temp.getStart() == 'w' && temp.getStart() == 'n' ) {
                createCurvePathView(temp.getMapX(), temp.getMapY(), 3);
            }*/
            createStraightPathView(temp.getMapX(), temp.getMapY(), false);
            temp = temp.getNext();
            System.out.println("durchgang");
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        schuelerweg.appendPfad('w','e',1,4);
        schuelerweg.appendPfad('w','e',2,4);
        schuelerweg.appendPfad('w','e',3,4);
        schuelerweg.appendPfad('w','e',4,4);
        renderWeg(schuelerweg);
    }
}