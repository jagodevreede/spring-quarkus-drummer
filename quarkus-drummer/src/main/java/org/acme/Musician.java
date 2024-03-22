package org.acme;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Musician {
    private final File toPlay;
    private final String pattern;
    private final String instrument;

    public Musician(String instrument, String pattern, String audioFolder) {
        this.pattern = pattern;
        this.instrument = instrument;
        this.toPlay = Arrays.stream(new File(audioFolder).listFiles())
                .filter(f -> f.getName().contains(instrument))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("instrument %s not found", instrument)));
    }

    public void play(short beat) {
        char c = pattern.charAt(beat - 1);
        if (c == 'x') {
            try {
                AudioInputStream stream = AudioSystem.getAudioInputStream(toPlay);
                var format = stream.getFormat();
                DataLine.Info dataLineInfo = new DataLine.Info(Clip.class, format);
                Clip clip = (Clip) AudioSystem.getLine(dataLineInfo);
                clip.open(stream);
                clip.setMicrosecondPosition(0);
                clip.start();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
