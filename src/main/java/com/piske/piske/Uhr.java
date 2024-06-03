package com.piske.piske;

import javafx.application.Platform;
import javafx.scene.image.ImageView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Uhr {
    private int zeit = 0;
    private ImageView uhrzeiger;

    public Uhr(ImageView uhrzeiger) {
        this.uhrzeiger = uhrzeiger;
        System.out.println("Uhrzeiger: " + uhrzeiger);
    }

    public void runTimer(int t) {
      zeit = t;
      double rotationswinkel = 360 / t;
      for (int i = 0; i < t; i++) {
            int finalI = i;
            delay(1000 * i, () -> {
                Platform.runLater(() -> {
                    uhrzeiger.setRotate(rotationswinkel*(finalI +1));
                });
            });
        }
    }

    public void delay(int milliseconds, Runnable task) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(task, milliseconds, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }
}
