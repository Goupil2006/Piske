package com.piske.piske;

import javafx.scene.media.Media;
import javafx.scene.media.MediaException;
import javafx.scene.media.MediaPlayer;

import java.io.File;
import java.nio.file.Paths;

public class Dankeschoen {
    public static MediaPlayer mp; //static um abbruch durch GC zu verhindern

    public Dankeschoen() {

    }

    public void sagDankeschoen() throws MediaException {
        Media media = new Media(getClass().getResource("/com/piske/piske/audio/Dankeschoen.mp3").toString());
        mp = new MediaPlayer(media);
        mp.play();
    }

    public void schießeLaut() {
         Media media = new Media(getClass().getResource("/com/piske/piske/audio/schuss.mp3").toString());
         mp = new MediaPlayer(media);
         mp.play();
    }

     public void beameMitarbeiter() {
         Media media = new Media(getClass().getResource("/com/piske/piske/audio/beam.mp3").toString());
         mp = new MediaPlayer(media);
         mp.play();
    }
}