package com.piske.piske;

import javafx.animation.Interpolator;
import javafx.animation.TranslateTransition;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

public class projectile {
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

    public projectile(int x, int y, double a, int v, int h, int w, AnchorPane screen){
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

    public void goProjectile(int x, int y){
        translate.setDuration(Duration.millis(5000));
        translate.setNode(imageView);
        translate.setToX(x * 72);
        translate.setToY(y * 72);
        setMapX(x);
        setMapY(y);
        translate.setInterpolator(Interpolator.LINEAR);
        translate.play();
    }
}



