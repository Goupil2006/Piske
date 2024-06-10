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
    private Station station;
    String type = "";
    ImageView imageViewp = new ImageView();
    TranslateTransition translate = new TranslateTransition();
    SchülerManager schülerManager;
    private AnchorPane screen;

    public Projectile(int x, int y, double a, int v, int h, int w, AnchorPane screen, int damage,
            SchülerManager schülerManager, Station station) {
        this.damage = damage;
        this.schülerManager = schülerManager;
        this.screen = screen;
        imageViewp.setLayoutX(x * 72 + 72 / 2);
        imageViewp.setLayoutY(y * 72 + 72 / 2);
        this.startx = x * 72 + 72 / 2;
        this.starty = y * 72 + 72 / 2;
        this.x = x * 72 + 72 / 2;
        this.y = y * 72 + 72 / 2;
        this.station = station;
        Image image1 = null;
        type = station.name;
        angle = a;
        velocity = v;
        height = h;
        width = w;
        // Image image = new
        // Image(getClass().getResourceAsStream("/com/piske/piske/Images/bullet.png"));
        switch (type) {
            case "Silli":
                image1 = new Image(getClass().getResourceAsStream("/com/piske/piske/Images/pommesbullet.png"));
                break;

            case "Ira":
                image1 = new Image(getClass().getResourceAsStream("/com/piske/piske/Images/currybullet.png"));
                break;

            case "Biene":
                image1 = new Image(getClass().getResourceAsStream("/com/piske/piske/Images/burgerbullet.png"));
                break;

            case "Conny":
                image1 = new Image(getClass().getResourceAsStream("/com/piske/piske/Images/bratbullet.png"));
                break;

            case "Evy":
                image1 = new Image(getClass().getResourceAsStream("/com/piske/piske/Images/cheesybullet.png"));
                break;

            default:
                image1 = new Image(getClass().getResourceAsStream("/com/piske/piske/Images/bullet.png"));
        }
        imageViewp.setImage(image1);
        imageViewp.toFront();
        Platform.runLater(() -> {
            screen.getChildren().add(imageViewp);
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
        // rotate projectile
        double nowangle = 0;
        int bonus = 0;
        double xval = target.getX() - this.startx;
        double yval = this.starty - target.getY();
        if (xval > 0 && yval < 0) {
            yval = Math.abs(yval);
            bonus = 90;
        } else if (xval < 0 && yval < 0) {
            yval = Math.abs(yval);
            xval = Math.abs(xval);
            bonus = 180;
        } else if (xval < 0 && yval > 0) {
            xval = Math.abs(xval);
            bonus = 270;
        } else if (xval > 0 && yval > 0) {
            bonus = 0;
        }

        if (yval == 0) {
            if (xval < 0) {
                nowangle = 270;
            } else if (xval > 0) {
                nowangle = 90;
            }
        } else if (xval == 0) {
            if (yval < 0) {
                nowangle = 180;
            } else if (yval > 0) {
                nowangle = 0;
            }
        } else {
            nowangle = Math.atan(yval / xval);
            nowangle = Math.toDegrees(nowangle);
            nowangle += bonus;
        }
        imageViewp.setRotate(nowangle);

        // move projectile
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

        delay(300, () -> {
            finished(giveMoney);
            // giveMoney.accept(this.target.health);
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
        Platform.runLater(() -> {
            imageViewp.setImage(null);
            this.screen.getChildren().remove(imageViewp);
        });

    }

    public void delay(int milliseconds, Runnable task) {
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        executor.schedule(task, milliseconds, TimeUnit.MILLISECONDS);
        executor.shutdown();
    }
}
