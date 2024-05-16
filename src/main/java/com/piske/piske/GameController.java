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
import javax.xml.datatype.Duration;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;


public class GameController implements Initializable {

    @FXML
    private AnchorPane gamescreen;

    @FXML
    private Button addStation1;

    Weg schuelerweg = new Weg();

    private Schüler[] schülers = new Schüler[1];

    public void createSchüler(int x, int y) { // TODO: Weg als übergabeparamether hinzufügen
        schülers[0] = new Schüler(x, y, gamescreen);
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
        Pfad temp = Weg.getHead();
        while (Weg.getTail() != temp) {
            if (temp.getStart() == 'n' && temp.getEnd() == 's' || temp.getStart() == 's' && temp.getEnd() == 'n') {
                createStraightPathView(temp.getMapX(), temp.getMapY(), true);
            } else if (temp.getStart() == 'e' && temp.getEnd() == 'w'
                    || temp.getStart() == 'w' && temp.getEnd() == 'e') {
                createStraightPathView(temp.getMapX(), temp.getMapY(), false);
            } else if (temp.getStart() == 'n' && temp.getEnd() == 'e'
                    || temp.getStart() == 'e' && temp.getEnd() == 'n') {
                createCurvePathView(temp.getMapX(), temp.getMapY(), 0);
            } else if (temp.getStart() == 's' && temp.getEnd() == 'e'
                    || temp.getStart() == 'e' && temp.getEnd() == 's') {
                createCurvePathView(temp.getMapX(), temp.getMapY(), 1);
            } else if (temp.getStart() == 's' && temp.getEnd() == 'w'
                    || temp.getStart() == 'w' && temp.getEnd() == 's') {
                createCurvePathView(temp.getMapX(), temp.getMapY(), 2);
            } else if (temp.getStart() == 'n' && temp.getEnd() == 'w'
                    || temp.getStart() == 'w' && temp.getEnd() == 'n') {
                createCurvePathView(temp.getMapX(), temp.getMapY(), 3);
            }
            temp = temp.getNext();
        }

    }

    public void renderMap() {

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        createSchüler(0,0);
        //Pfad erstellen
        schuelerweg.appendPfad('w','e',0,4);
        schuelerweg.appendPfad('w','e',1,4);
        schuelerweg.appendPfad('w','n',2,4);
        schuelerweg.appendPfad('s','n',2,3);
        schuelerweg.appendPfad('s','e',2,2);
        schuelerweg.appendPfad('w','e',3,2);
        schuelerweg.appendPfad('w','e',4,2);
        schuelerweg.appendPfad('w','e',5,2);
        schuelerweg.appendPfad('w','e',6,2);
        schuelerweg.appendPfad('w','e',7,2);
        schuelerweg.appendPfad('w','e',8,2);
        schuelerweg.appendPfad('w','e',9,2);
        schuelerweg.appendPfad('w','s',10,2);
        schuelerweg.appendPfad('n','s',10,3);
        schuelerweg.appendPfad('n','s',10,4);
        schuelerweg.appendPfad('n','s',10,5);
        schuelerweg.appendPfad('n','s',10,6);
        schuelerweg.appendPfad('n','w',10,7);
        schuelerweg.appendPfad('e','w',9,7);
        schuelerweg.appendPfad('e','w',8,7);
        schuelerweg.appendPfad('e','w',7,7);
        schuelerweg.appendPfad('e','w',6,7);
        schuelerweg.appendPfad('e','w',5,7);
        schuelerweg.appendPfad('e','w',4,7);
        schuelerweg.appendPfad('e','w',3,7);
        schuelerweg.appendPfad('e','n',2,7);
        schuelerweg.appendPfad('s','n',2,6);
        schuelerweg.appendPfad('s','w',2,5);
        schuelerweg.appendPfad('e','w',1,5);
        schuelerweg.appendPfad('e','w',0,5);
        schuelerweg.appendPfad('e','w',-1,5);
        renderWeg(schuelerweg);
        schülers[0].goWeg(schuelerweg);


        delay(1500, () -> {
           System.out.println(schülers[0].getX());
            System.out.println(schülers[0].getY());
        });

        delay(5500, () -> {
            System.out.println(schülers[0].getX());
            System.out.println(schülers[0].getY());
        });
    }

    public void delay(int milliseconds, Runnable task) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(task, milliseconds, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }
}