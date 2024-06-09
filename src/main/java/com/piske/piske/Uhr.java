package com.piske.piske;

import javafx.application.Platform;
import javafx.scene.image.ImageView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Uhr {
    private int zeit = 0;
    private ImageView uhrzeiger;
    double rotationswinkel;
    double temp;

    public Uhr(ImageView uhrzeiger) {
        this.uhrzeiger = uhrzeiger;
        System.out.println("Uhrzeiger: " + uhrzeiger);
    }

    public void runTimer(int t) {
        zeit = t;
        this.rotationswinkel = 360 / t;
        temp = Math.random();
        run(0, temp);
    }

    public void run(int curT, double temp) {
        delay(1000, () -> {
            Platform.runLater(() -> {
                uhrzeiger.setRotate(rotationswinkel * curT);
            });
            if (curT <= zeit && temp == this.temp) {
                run(curT + 1, temp);
            }
        });
    }

    public void stop() {
        this.temp = Math.random();
    }

    public void delay(int milliseconds, Runnable task) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(task, milliseconds, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }
}
