package com.piske.piske;

import javafx.animation.*;
import javafx.application.Platform;
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
    private Schüler target;
    ImageView imageViewp = new ImageView();
    TranslateTransition translate = new TranslateTransition();
    SchülerManager schülerManager;

    public Projectile(int x, int y, double a, int v, int h, int w, AnchorPane screen, int damage, SchülerManager schülerManager) {
        System.out.println("test");
        this.damage = damage;
        this.schülerManager = schülerManager;
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
        Platform.runLater(() -> {
            screen.getChildren().add(imageViewp);
            System.out.println("test4");
        });

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

    public void goProjectile(Schüler target, Consumer<Integer> giveMoney) {
        this.target = target;
        System.out.println("going");
        // rotate projectile
        RotateTransition rt = new RotateTransition(Duration.millis(50), imageViewp);
        double nowangle = 0;
        int bonus = 0;
        System.out.println(this.getX());
        System.out.println(target.getX());
        System.out.println(this.getY());
        System.out.println(target.getY());
        double xval = target.getX() - this.getX();
        double yval = this.getY() - target.getY();
        System.out.println(xval);
        System.out.println(yval);
        if (xval > 0 && yval < 0){
            yval = Math.abs(yval);
            bonus = 90;
        } else if(xval < 0 && yval < 0){
            yval = Math.abs(yval);
            xval = Math.abs(xval);
            bonus = 180;
        } else if (xval < 0 && yval > 0){
            xval = Math.abs(xval);
            bonus = 270;
        }
        System.out.println("bonus: " + bonus);
        nowangle = Math.atan(yval / xval);
        System.out.println(nowangle);
        nowangle = Math.toDegrees(nowangle) + bonus;
        System.out.println(nowangle);
//        if (nowangle < 0) {
//            nowangle += 360;
//        }
        System.out.println(nowangle);
        rt.setToAngle(Math.abs(nowangle));
        rt.setCycleCount(1);
        rt.setAutoReverse(true);
        rt.play();

        translate.setDuration(Duration.millis(300));
        translate.setNode(imageViewp);
        translate.setToX(target.getX() - startx + 72 / 2);
        translate.setToY(target.getY() - starty + 72 / 2);

        // this.x = target.getX() - startx + 72 / 2;
        // this.y = target.getY() - starty + 72 / 2;
        // setMapX(target.getX());
        // setMapY(target.getY());
        translate.setInterpolator(Interpolator.LINEAR);
        translate.play();

        delay( 300, () -> {
            finished(giveMoney);
            //giveMoney.accept(this.target.health);
        });


        // Schüler finaltarget = target;
        // delay(100, () -> {
        // checkColision.accept(this);
        // goProjectile(finaltarget, checkColision);
        // });
    }

    public void finished(Consumer<Integer> giveMoney) {
        this.target.hit(this.damage, schülerManager, giveMoney);
        this.hit();
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
