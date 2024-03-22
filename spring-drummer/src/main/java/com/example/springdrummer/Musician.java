package com.example.springdrummer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

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
        LOG.info("Got beat: {}", message.getPayload());
    }
}
