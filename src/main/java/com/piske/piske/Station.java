package com.piske.piske;

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

    public Station(InterfaceController interfaceController, GameController gameController, StationController stationController, AnchorPane plane, int x,
            int y, String grafic) {
        this.interfaceController = interfaceController;
        this.gameController = gameController;
        this.stationController = stationController;
        this.plane = plane;

        if (this.interfaceController.getMoney() < 50) {
            return;
        }
        this.interfaceController.changeAmount(-50);
        ImageView imageView = new ImageView();
        imageView.setLayoutX(x * 72);
        imageView.setLayoutY(y * 72);
        Image image = new Image(getClass().getResourceAsStream(grafic));
        imageView.setImage(image);
        this.plane.getChildren().add(imageView);

        this.shot(x, y);
    }

    private void shot(int x, int y) {
        // find Target
//        Schüler target = this.gameController.schülerManager.getSchüler(x, y, 300);
//
//        System.out.println("active");
//        if (target != null) {
//            System.out.println("created");
//            this.gameController.createProjectile(x, y, 0, 0, 0, 0, target);
//        }
//
//        int finalx = x;
//        int finaly = y;
//        delay(1000, () -> {
//            if (stationController.active) {
//                this.shot(finalx, finaly);
//            }
//        });
    }

    public void delay(int milliseconds, Runnable task) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(task, milliseconds, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }
}
