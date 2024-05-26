package com.piske.piske;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Projectile {
    private int startx = 0;
    private int starty = 0;
    public int mapY;
    public int mapX;
    public double angle;
    public int velocity;
    public int height;
    public int width;
    ImageView imageView = new ImageView();
    private AnchorPane screen;
    TranslateTransition translate = new TranslateTransition();

    public Projectile(int x, int y, double a, int v, int h, int w, AnchorPane screen){
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        angle = a;
        velocity = v;
        height = h;
        width = w;
        this.screen = screen;
        Image image = new Image(getClass().getResourceAsStream("/com/piske/piske/Images/bullet.png"));
        imageView.setImage(image);
        screen.getChildren().add(imageView);
    }

    public int getMapY(){
        return mapY;
        //return translate.getCurrentTime().toMillis() / 500 * ((int) translate.getToY() - this.mapY) + this.mapY + starty;
    }

    public void setMapY(int y){mapY = y;}

    public int getMapX(){
        return mapX;
        //return translate.getCurrentTime().toMillis() / 500 * ((int) translate.getToX() - this.mapX) + this.mapX + startx;
    }

    public void setMapX(int x){mapX = x;}

    public int getVelocity(){return velocity;}

    public void setVelocity(int v){velocity = v;}

    public int getHeight(){return height;}

    public void setHeight(int h){height = h;}

    public int getWidth(){return width;}

    public void setWidth(int w){width = w;}

    public double getAngle(){return angle;}

    public void setAngle(double a){angle = a;}

    public void goProjectile(Schüler target){
        System.out.println("going");
        translate.setDuration(Duration.millis(500));
        translate.setNode(imageView);
        translate.setToX(target.getX());
        translate.setToY(target.getY());
        setMapX(target.getX());
        setMapY(target.getY());
        translate.setInterpolator(Interpolator.LINEAR);
        translate.play();
        Schüler finaltarget = target;
        delay(500, () -> {
            goProjectile(finaltarget);
        });
    }

    public void delay(int milliseconds, Runnable task) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(task, milliseconds, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }
}



