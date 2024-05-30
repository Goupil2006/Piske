package com.piske.piske;

import javafx.animation.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class Projectile {
    private int startx = 0;
    private int starty = 0;
    public int mapY;
    public int mapX;
    public double angle;
    public int velocity;
    public int height;
    public int width;
    private int damage = 20;
    private int x;
    private int y;
    ImageView imageViewp = new ImageView();
    TranslateTransition translate = new TranslateTransition();

    public Projectile(int x, int y, double a, int v, int h, int w, AnchorPane screen) {
        System.out.println("test");
        imageViewp.setLayoutX(x * 72);
        imageViewp.setLayoutY(y * 72);
        this.startx = x * 72;
        this.starty = y * 72;
        this.x = x * 72;
        this.y = y * 72;
        angle = a;
        velocity = v;
        height = h;
        width = w;
        System.out.println("test2");
        Image image = new Image(getClass().getResourceAsStream("/com/piske/piske/Images/bullet.png"));
        System.out.println("test3");
        imageViewp.setImage(image);
        System.out.println("test3");
        screen.getChildren().add(imageViewp);
        System.out.println("test4");
    }

    public int getMapY() {
        return mapY;
        // return translate.getCurrentTime().toMillis() / 500 * ((int)
        // translate.getToY() - this.mapY) + this.mapY + starty;
    }

    public int getDamage() {
        return this.damage;
    }

    public void setMapY(int y) {
        mapY = y;
    }

    public int getMapX() {
        return mapX;
        // return translate.getCurrentTime().toMillis() / 500 * ((int)
        // translate.getToX() - this.mapX) + this.mapX + startx;
    }

    public void setMapX(int x) {
        mapX = x;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int v) {
        velocity = v;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int h) {
        height = h;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int w) {
        width = w;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double a) {
        angle = a;
    }

    public int getX() {
        return (int) translate.getCurrentTime().toMillis() / 1000 * ((int) translate.getToX() - this.x) + this.x
                + startx;
    }

    public int getY() {
        return (int) translate.getCurrentTime().toMillis() / 1000 * ((int) translate.getToY() - this.y) + this.y
                + starty;
    }

    public void goProjectile(Schüler target, Consumer<Projectile> checkColision) {
        System.out.println("going");
        // rotate projectile
        RotateTransition rt = new RotateTransition(Duration.millis(300), imageViewp);
        double nowangle = 0;
        int xval = target.getX() - this.getX();
        int yval = target.getY() - this.getY();
//        System.out.println(this.getX());
//        System.out.println(this.getY());
//        System.out.println(target.getX());
//        System.out.println(target.getY());
//        System.out.println(xval);
//        System.out.println(yval);
        nowangle = xval / Math.sqrt(Math.pow(xval, 2) + Math.pow(yval, 2));
        nowangle = Math.asin(nowangle) * 180 / Math.PI;
        System.out.println(nowangle);
        rt.setToAngle(nowangle);
        rt.setCycleCount(1);
        rt.setAutoReverse(true);
        rt.play();

        translate.setDuration(Duration.millis(2000));
        translate.setNode(imageViewp);
        System.out.println(String.valueOf(target.getX() - startx + 72 / 2) + " "
                + String.valueOf(target.getY() - starty + 72 / 2));
        translate.setToX(target.getX() - startx + 72 / 2 + ((target.getX() - startx + 72 / 2 - startx)));
        translate.setToY(target.getY() - starty + 72 / 2 + ((target.getY() - starty + 72 / 2 - starty)));

        // this.x = target.getX() - startx + 72 / 2;
        // this.y = target.getY() - starty + 72 / 2;
        // setMapX(target.getX());
        // setMapY(target.getY());
        translate.setInterpolator(Interpolator.LINEAR);
        translate.play();
        // Schüler finaltarget = target;
        // delay(100, () -> {
        // checkColision.accept(this);
        // goProjectile(finaltarget, checkColision);
        // });
    }

    public void hit() {
        translate.stop();
        imageViewp.setImage(null);
    }

    public void delay(int milliseconds, Runnable task) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(task, milliseconds, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }
}
