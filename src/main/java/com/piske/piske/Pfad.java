package com.piske.piske;

public class Pfad {
    public char start = ' ';
    public char ende = ' ';
    public Pfad next = null;
    public int mapX = 0;
    public int mapY = 0;

    public Pfad(char s, char e, int x, int y) {
        start = s;
        ende = e;
        mapX = x;
        mapY = y;

    }

    public char getStart() {
        return start;
    }

    public void setStart(char s) {
        start = s;
    }

    public char getEnd() {
        return ende;
    }

    public void setEnd(char e) {
        ende = e;
    }


    public Pfad getNext() {
        return next;
    }

    public void setNext(Pfad n) {
        next = n;
    }

    public int getMapX() {
        return mapX;
    }

    public void setMapX(int x) {
        mapX = x;
    }

    public int getMapY() {
        return mapY;
    }

    public void setMapY(int y) {
        mapY = y;
    }
}
