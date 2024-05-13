package com.piske.piske;

public class Weg {
    Pfad head = null;
    Pfad tail = null;

    public void weg() {
    }

    public void appendPfad(char s, char e, int x, int y){
        Pfad temp = new Pfad(s,e,x,y);
        if(head == null) {
            head = temp;
            tail = temp;
        } else {
            //temp.setPrevious(tail);
            //tail.setNext(temp);
        }
    }
}