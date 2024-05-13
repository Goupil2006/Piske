package com.piske.piske;

public class Weg {
    static Pfad head = null;
    static Pfad tail = null;

    public void weg() {
    }

    public void appendPfad(char s, char e, int x, int y){
        Pfad temp = new Pfad(s,e,x,y);
        if(head == null) {
            head = temp;
            tail = temp;
        } else {
            temp.setPrevious(Weg.getTail());
            tail.setNext(temp);
        }
    }

    public static Pfad getTail() {
        return tail;
    }
}