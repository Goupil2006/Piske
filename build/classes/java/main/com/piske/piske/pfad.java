public class pfad {
    public char start = null;
    public char end = null;
    public weg previous = null;
    public weg next = null;
    public int mapX = null;
    public int mapY = null;

    public void pfad(char s, char e, int x, int y) {
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
        return end;
    }

    public void setEnd(char e) {
        end = e;
    }

    public weg getPrevious() {
        return previous;
    }

    public void setPrevious(weg p) {
        previous = p;
    }

    public weg getNext() {
        return next;
    }

    public void setNext(weg n) {
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