package com.example.springdrummer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import java.io.File;
import java.util.Arrays;

public class Musician extends TextWebSocketHandler {
    private static final Logger LOG = LoggerFactory.getLogger(Musician.class);
    private final File toPlay;
    private final String pattern;
    private final String instrument;
    private String audioFolder;

    public Musician(String instrument, String pattern, String audioFolder) {
        this.pattern = pattern;
        this.instrument = instrument;
        this.toPlay = Arrays.stream(new File(audioFolder).listFiles())
                .filter(f -> f.getName().contains(instrument))
                .findFirst()
                .orElseThrow(() -> new RuntimeException(String.format("instrument %s not found", instrument)));
    }

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        short beat = Short.parseShort(message.getPayload());
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
