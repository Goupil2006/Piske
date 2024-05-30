package com.piske.piske;

public class Weg {
    static Pfad head = null;
    static Pfad tail = null;

    public void weg() {
    }

    public void appendPfad(char s, char e, int x, int y) {
        Pfad temp = new Pfad(s, e, x, y);
        if (this.head == null) {
            head = temp;
            tail = temp;
        } else {
            tail.setNext(temp);
            tail = temp;
        }
    }

    public static Pfad getTail() {
        return tail;
    }

    public static Pfad getHead() {
        return head;
    }
}