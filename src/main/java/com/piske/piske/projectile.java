package com.piske.piske;

public class projectile {
    public int mapY;
    public int mapX;
    public double angle;
    public int velocity;
    public int height;
    public int width;

    public projectile(int x, int y, double a, int v, int h, int w){
        mapX = x;
        mapY = y;
        angle = a;
        velocity = v;
        height = h;
        width = w;
    }

    public int getMapY(){return mapY;}

    public void setMapY(int y){mapY = y;}

    public int getMapX(){return mapX;}

    public void setMapX(int x){mapX = x;}

    public int getVelocity(){return velocity;}

    public void setVelocity(int v){velocity = v;}

    public int getHeight(){return height;}

    public void setHeight(int h){height = h;}

    public int getWidth(){return width;}

    public void setWidth(int w){width = w;}

    public double getAngle(){return angle;}

    public void setAngle(double a){angle = a;}

}


