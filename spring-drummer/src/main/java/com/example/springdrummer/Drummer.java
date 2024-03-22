package com.example.springdrummer;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;

import java.net.URI;

@Component
public class Drummer {
    private static final Logger LOG = LoggerFactory.getLogger(Drummer.class);

    @Value("${ws-url}")
    private URI websocketUri;
    @Value("${audioFolder}")
    private String audioFolder;

    @Autowired
    ConductorRestClient conductorRestClient;
    @Autowired
    DrumPatternRepository drumPatternRepository;

    @PostConstruct
    public void init() {
        String instrument = conductorRestClient.getInstrument();
        LOG.info("Got instrument {}", instrument);

        DrumPattern pattern = drumPatternRepository.findByName(instrument);
        LOG.info("Will play pattern {}", pattern.pattern);

        WebSocketClient client = new StandardWebSocketClient();
        client.execute(new Musician(instrument, pattern.pattern, audioFolder),
                new WebSocketHttpHeaders(), websocketUri);
    }
}
