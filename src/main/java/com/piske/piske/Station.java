package com.piske.piske;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Station {
    InterfaceController interfaceController;
    GameController gameController;
    StationController stationController;
    AnchorPane plane;

    private String name;

    private int damage = 10;

    public Station(InterfaceController interfaceController, GameController gameController,
            StationController stationController, AnchorPane plane, int x,
            int y, String grafic, String name) {
        this.interfaceController = interfaceController;
        this.gameController = gameController;
        this.stationController = stationController;
        this.plane = plane;
        this.name = name;

        if (this.interfaceController.getMoney() < 50) {
            return;
        }
        this.interfaceController.changeAmount(-50);
        ImageView imageView = new ImageView();
        imageView.setLayoutX(x * 72);
        imageView.setLayoutY(y * 72);
        Image image = new Image(getClass().getResourceAsStream(grafic));
        imageView.setImage(image);
        imageView.setOnMouseClicked(event -> {
           // Circle c = new Circle(300, Color);
        });
        this.plane.getChildren().add(imageView);

        shot(x, y);
    }

    private void shot(int x, int y) {
        // find Target
        Schüler target = this.gameController.schülerManager.getSchüler(x, y, 300);

        System.out.println("active");
        if (target != null) {
            System.out.println("created");
            this.gameController.createProjectile(x, y, 0, 0, 0, 0, target, this.damage);
        }

        int finalx = x;
        int finaly = y;
        delay(1000, () -> {
            if (stationController.active) {
                this.shot(finalx, finaly);
            }
        });
    }

    public void upgrade() {
        if (this.interfaceController.getMoney() < this.damage * 5) {
            return;
        }
        this.interfaceController.changeAmount(-this.damage * 5);
        this.damage += 10;
    }

    public String getName() {
        return name;

    }

    public int getDamage() {
        return this.damage;

    }

    public void delay(int milliseconds, Runnable task) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(task, milliseconds, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }
}
