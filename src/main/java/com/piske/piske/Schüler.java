package com.piske.piske;

import javafx.animation.Interpolator;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
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
    ProgressBar healthBar = new ProgressBar();
    TranslateTransition translate2 = new TranslateTransition();
    public int initalhealth = 20;
    public int health = initalhealth;
    public double speed = 1;
    public double position = 0;
    public SchülerManager schülerManager;
    private GameController gameController;

    public Schüler(int x, int y, AnchorPane screen, int type, SchülerManager schülerManager,
            GameController gameController) {
        this.gameController = gameController;
        this.schülerManager = schülerManager;
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
                speed = 1;
                break;

            case 2:
                image = new Image(getClass().getResourceAsStream("/com/piske/piske/Images/Georgios.png"));
                health = 80;
                initalhealth = 80;
                speed = 1.2;
                break;

            case 3:
                image = new Image(getClass().getResourceAsStream("/com/piske/piske/Images/Lukas.png"));
                health = 200;
                initalhealth = 200;
                speed = 1.5;
                break;

            case 4:
                image = new Image(getClass().getResourceAsStream("/com/piske/piske/Images/Paul.png"));
                health = 1000;
                initalhealth = 1000;
                speed = 2;

                break;
            default:
                image = new Image(getClass().getResourceAsStream("/com/piske/piske/Images/Paul.png"));
                health = 3000;
                initalhealth = 3000;
                speed = 2;
                break;
        }
        imageView.setImage(image);
        screen.getChildren().add(imageView);

        // Create a health bar for the Schüler
        healthBar.setLayoutX(x);
        healthBar.setLayoutY(y - 25);
        healthBar.setPrefWidth(72);
        healthBar.setStyle(
                "-fx-accent: #DD0000;");
        healthBar.setProgress((double) health / initalhealth);
        screen.getChildren().add(healthBar);
    }

    public void goWeg(Weg way) {
        Pfad temp = way.getHead();
        imageView.setLayoutX(temp.mapX * 72);
        imageView.setLayoutY(temp.mapY * 72);
        startx = temp.mapX * 72;
        starty = temp.mapY * 72;
        imageView.toFront();
        // Move the health bar along with the Schüler
        healthBar.setLayoutX(temp.mapX * 72);
        healthBar.setLayoutY(temp.mapY * 72 - 25);
        temp = temp.getNext();
        goNextPfad(temp);
    }

    private void goNextPfad(Pfad pfad2) {
        final Pfad[] pfad = { pfad2 };
        Platform.runLater(() -> {
            this.position += (double) 1;
            // Move the Schüler using a TranslateTransition (Animation)
            translate.setDuration(Duration.millis(1000 * speed));
            translate.setNode(imageView);
            translate.setToX((double) pfad[0].mapX * 72);
            translate.setToY((double) pfad[0].mapY * 72 - starty);
            this.x = pfad[0].mapX * 72;
            this.y = pfad[0].mapY * 72 - starty;
            translate.setInterpolator(Interpolator.LINEAR);

            // Move the health bar using a TranslateTransition (Animation)
            translate2.setDuration(Duration.millis(1000 * speed));
            translate2.setNode(healthBar);
            translate2.setToX((double) pfad[0].mapX * 72);
            translate2.setToY((double) pfad[0].mapY * 72 - starty);
            translate2.setInterpolator(Interpolator.LINEAR);

            // Play both animations at the same time
            ParallelTransition parallelTransition = new ParallelTransition();
            parallelTransition.getChildren().addAll(translate, translate2);
            parallelTransition.play();

            pfad[0] = pfad[0].getNext();
            Pfad finalPfad = pfad[0];
            parallelTransition.setOnFinished(e -> {
                if (health > 0) {
                    if (finalPfad != null) {
                        goNextPfad(finalPfad);
                    } else {
                        Platform.runLater(() -> {
                            screen.getChildren().remove(healthBar);
                            screen.getChildren().remove(imageView);
                        });
                        try {
                            this.gameController.endGame(2);
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                        this.schülerManager.deleteSchüler(this);

                    }
                }

            });
        });
    }

    public void hit(int damage, SchülerManager schülerManager, Consumer<Integer> giveMoney) {
        health -= damage;
        if (health <= 0) {
            // give player money
            giveMoney.accept(this.initalhealth / 5);
            // delete Schüler
            Platform.runLater(() -> {
                screen.getChildren().remove(healthBar);
                screen.getChildren().remove(imageView);
            });
            schülerManager.deleteSchüler(this);
        } else {
            // set the health bar to the new health
            healthBar.setProgress((double) health / initalhealth);
        }

    }

    public int getX() {
        // Calculate the current x position of the Schüler
        return (int) ((int) translate.getCurrentTime().toMillis() / 1000 * this.speed
                * ((int) translate.getToX() - this.x)
                + this.x
                + startx);
    }

    public int getY() {
        // Calculate the current y position of the Schüler
        return (int) ((int) translate.getCurrentTime().toMillis() / 1000 * this.speed
                * ((int) translate.getToY() - this.y)
                + this.y
                + starty);
    }
}
