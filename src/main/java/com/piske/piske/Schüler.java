package com.piske.piske;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.util.function.Consumer;

public class Schüler {

    private int startx = 0;
    private int starty = 0;
    private int x = 0;
    private int y = 0;
    private int type = 1;
    ImageView imageView = new ImageView();
    private AnchorPane screen;
    TranslateTransition translate = new TranslateTransition();
    public int initalhealth = 20;
    public int health = initalhealth;

    public Schüler(int x, int y, AnchorPane screen, int type) {
        this.screen = screen;
        this.type = type;
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        Image image;
        switch (type) {
            case 1:
                image = new Image(getClass().getResourceAsStream("/com/piske/piske/Images/Marc.png"));
                health = 20;
                initalhealth = 20;
                break;

            case 2:
                image = new Image(getClass().getResourceAsStream("/com/piske/piske/Images/Georgios.png"));
                health = 50;
                initalhealth = 50;
                break;

            case 3:
                image = new Image(getClass().getResourceAsStream("/com/piske/piske/Images/Lukas.png"));
                health = 200;
                initalhealth = 200;
                break;

            case 4:
                image = new Image(getClass().getResourceAsStream("/com/piske/piske/Images/Paul.png"));
                health = 500;
                initalhealth = 500;

                break;
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
        imageView.setImage(image);
        screen.getChildren().add(imageView);
    }

    public void goWeg(Weg way) {
        Pfad temp = way.getHead();
        imageView.setLayoutX(temp.mapX * 72);
        imageView.setLayoutY(temp.mapY * 72);
        startx = temp.mapX * 72;
        starty = temp.mapY * 72;
        imageView.toFront();
        temp = temp.getNext();
        goNextPfad(temp);
    }

    private void goNextPfad(Pfad pfad) {
        translate.setDuration(Duration.millis(1000));
        translate.setNode(imageView);
        translate.setToX((double) pfad.mapX * 72);
        translate.setToY((double) pfad.mapY * 72 - starty);
        this.x = pfad.mapX * 72;
        this.y = pfad.mapY * 72 - starty;
        translate.setInterpolator(Interpolator.LINEAR);
        translate.play();
        pfad = pfad.getNext();
        Pfad finalPfad = pfad;
        translate.setOnFinished(e -> {
            if (finalPfad != null) {
                goNextPfad(finalPfad);
            } else {
                screen.getChildren().remove(imageView);
            }
        });
    }

    public void hit(int damage, SchülerManager schülerManager, Consumer<Integer> giveMoney) {
        health -= damage;
        System.out.println(health);
        if (health <= 0) {
            System.out.println("hit");
            giveMoney.accept(this.initalhealth / 5);
            Platform.runLater(() -> {
                screen.getChildren().remove(imageView);
            });
            schülerManager.deleteSchüler(this);

        }

    }

    public int getX() {
        return (int) translate.getCurrentTime().toMillis() / 1000 * ((int) translate.getToX() - this.x) + this.x
                + startx;
    }

    public int getY() {
        return (int) translate.getCurrentTime().toMillis() / 1000 * ((int) translate.getToY() - this.y) + this.y
                + starty;
    }
}
