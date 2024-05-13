public class weg {
 pfad head = null;
 pfad tail = null;

 public void weg() {
 }

 public void appendPfad(char s, char e, int x, int y){
   pfad temp = new pfad(s,e,x,y);
   if(head == null) {
    head = temp;
    tail = temp;
   } else {
      temp.setPrevious(tail);
      tail.setNext(temp);
   }
 }
}