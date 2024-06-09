package com.piske.piske;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Station {
    InterfaceController interfaceController;
    GameController gameController;
    StationController stationController;
    AnchorPane plane;

    public String name;

    public int originaldamage = 10;
    public int damage = 10;
    public int range = 300;
    public int speed = 1000;
    public int price = 50;

    public boolean ecsists = true;

    public ImageView imageView = new ImageView();

    public Station(InterfaceController interfaceController, GameController gameController,
            StationController stationController, AnchorPane plane, int x,
            int y, String grafic, String name, int range, int speed, int damage, int price) throws IOException {
        this.interfaceController = interfaceController;
        this.gameController = gameController;
        this.stationController = stationController;
        this.plane = plane;
        this.name = name;

        this.damage = damage;
        this.range = range;
        this.speed = speed;
        this.originaldamage = damage;
        this.price = price;

        if (this.interfaceController.getMoney() < this.price) {
            return;
        }
        this.interfaceController.changeAmount(this.price * -1);

        imageView.setLayoutX(x * 72);
        imageView.setLayoutY(y * 72);
        Image image = new Image(getClass().getResourceAsStream(grafic));
        imageView.setImage(image);
        this.plane.getChildren().add(imageView);

        shot(x, y);
    }

    public void changeecsists() {
        this.ecsists = false;
    }

    private void shot(int x, int y) throws IOException {
        // find Target
        Schüler target = this.gameController.schülerManager.getSchüler(x, y, this.range);

        System.out.println("active");
        if (target != null) {
            this.gameController.createProjectile(x, y, 0, 0, 0, 0, target, this.damage);
        }

        int finalx = x;
        int finaly = y;
        delay(this.speed, () -> {
            if (stationController.active && this.ecsists) {
                try {
                    this.shot(finalx, finaly);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    public void upgrade() {
        if (this.interfaceController.getMoney() < this.damage * 5) {
            return;
        }
        this.interfaceController.changeAmount(-this.damage * 5);
        this.damage += originaldamage;
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
