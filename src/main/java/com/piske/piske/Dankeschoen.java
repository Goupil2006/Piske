package com.piske.piske;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class Dankeschoen {
    public static MediaPlayer mp; //static um abbruch durch GC zu verhindern

    public void sagDankeschoen() {
         Media media = new Media("/com/piske/piske/audio/piske-pr-komp (online-audio-converter.com)-[AudioTrimmer.com].mp3");
         mp = new MediaPlayer(media);
         mp.play();
    }
}