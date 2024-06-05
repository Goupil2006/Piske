package com.piske.piske;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

public class Utils {
    // Your code here
    public static void createStraightPathView(int x, int y, boolean r, AnchorPane gamescreen) {
        x = x * 72;
        y = y * 72;
        y += 21;
        ImageView imageView = new ImageView();
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        if (r) {
            imageView.setRotate(90);
        } else {
            imageView.setRotate(0);
        }
        Image image = new Image(Utils.class.getResourceAsStream("/com/piske/piske/Images/weg.png"));
        imageView.setImage(image);
        gamescreen.getChildren().add(imageView);
    }

    public static void createCurvePathView(int x, int y, int r, AnchorPane gamescreen) {
        x = x * 72;
        y = y * 72;

        ImageView imageView = new ImageView();
        if (r == 0) {
            imageView.setRotate(0);
            x += 21;
        } else if (r == 1) {
            imageView.setRotate(90);
            y += 21;
            x += 21;
        } else if (r == 2) {
            imageView.setRotate(180);
            y += 21;
        } else if (r == 3) {
            imageView.setRotate(270);
        }
        imageView.setLayoutX(x);
        imageView.setLayoutY(y);
        Image image = new Image(Utils.class.getResourceAsStream("/com/piske/piske/Images/wegL.png"));
        imageView.setImage(image);
        gamescreen.getChildren().add(imageView);
    }

    public static void renderWeg(Weg w, AnchorPane gamescreen) {
        Pfad temp = Weg.getHead();
        while (Weg.getTail() != temp) {
            if (temp.getStart() == 'n' && temp.getEnd() == 's' || temp.getStart() == 's' && temp.getEnd() == 'n') {
                createStraightPathView(temp.getMapX(), temp.getMapY(), true, gamescreen);
            } else if (temp.getStart() == 'e' && temp.getEnd() == 'w'
                    || temp.getStart() == 'w' && temp.getEnd() == 'e') {
                createStraightPathView(temp.getMapX(), temp.getMapY(), false, gamescreen);
            } else if (temp.getStart() == 'n' && temp.getEnd() == 'e'
                    || temp.getStart() == 'e' && temp.getEnd() == 'n') {
                createCurvePathView(temp.getMapX(), temp.getMapY(), 0, gamescreen);
            } else if (temp.getStart() == 's' && temp.getEnd() == 'e'
                    || temp.getStart() == 'e' && temp.getEnd() == 's') {
                createCurvePathView(temp.getMapX(), temp.getMapY(), 1, gamescreen);
            } else if (temp.getStart() == 's' && temp.getEnd() == 'w'
                    || temp.getStart() == 'w' && temp.getEnd() == 's') {
                createCurvePathView(temp.getMapX(), temp.getMapY(), 2, gamescreen);
            } else if (temp.getStart() == 'n' && temp.getEnd() == 'w'
                    || temp.getStart() == 'w' && temp.getEnd() == 'n') {
                createCurvePathView(temp.getMapX(), temp.getMapY(), 3, gamescreen);
            }
            temp = temp.getNext();
        }

    }
}
