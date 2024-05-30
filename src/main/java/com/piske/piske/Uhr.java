package com.piske.piske;

import javafx.scene.image.ImageView;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Uhr {
    private int zeit = 0;
    private ImageView uhrzeiger;

    public Uhr(ImageView uhrzeiger) {
        this.uhrzeiger = uhrzeiger;
    }

    public int runTimer(int t) {
      zeit = t;
      double rotationswinkel = 360 / t;
      double rotationaktuell = 0;
        for (int i = 0; i < 12; i++) {
            rotationaktuell += rotationswinkel;
            double finalRotationaktuell = rotationaktuell;
            delay(1000, () -> {
                uhrzeiger.setRotate(finalRotationaktuell);
                System.out.println(finalRotationaktuell +"");
            });
        }
    return 1;
    }

    public void delay(int milliseconds, Runnable task) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(task, milliseconds, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }
}
