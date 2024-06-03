package com.piske.piske;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Dankeschoen {
    public static MediaPlayer mp; //static um abbruch durch GC zu verhindern

    public void sagDankeschoen() {
         Media media = new Media("/com/piske/piske/audio/Dankeschoen.mp3");
         mp = new MediaPlayer(media);
         mp.play();
    }

    public void schießeLaut() {
         Media media = new Media("/com/piske/piske/audio/schuss.mp3");
         mp = new MediaPlayer(media);
         mp.play();
    }

     public void beameMitarbeiter() {
         Media media = new Media("/com/piske/piske/audio/beam.mp3");
         mp = new MediaPlayer(media);
         mp.play();
    }
}