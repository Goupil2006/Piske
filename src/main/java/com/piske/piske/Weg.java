package com.piske.piske;

import org.json.JSONArray;

public class Weg {
    public static Pfad head = null;
    public static Pfad tail = null;

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

    public void reset() {
        this.head = null;
        this.tail = null;
    }

    public static Pfad getTail() {
        return tail;
    }

    public static Pfad getHead() {
        return head;
    }

    public boolean isPossibleToWalkBySchuler() {
        if (head == null || tail == null) {
            return false;
        }
        if (head.getMapX() != 0 || tail.getMapX() != 0) {
            return false;
        }

        Pfad current = head;
        while (current != null && current.getNext() != null) {
            if (!isNextToEachOther(current, current.getNext())) {
                return false;
            }
            current = current.getNext();
        }
        return true;
    }

    private boolean isNextToEachOther(Pfad p1, Pfad p2) {
        int xDiff = Math.abs(p1.getMapX() - p2.getMapX());
        int yDiff = Math.abs(p1.getMapY() - p2.getMapY());
        return (xDiff == 1 && yDiff == 0) || (xDiff == 0 && yDiff == 1);
    }
}