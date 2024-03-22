package org.acme;

import io.quarkus.runtime.StartupEvent;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import jakarta.websocket.ClientEndpoint;
import jakarta.websocket.ContainerProvider;
import jakarta.websocket.OnMessage;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

@ApplicationScoped
@ClientEndpoint
public class Drummer {
    private static final Logger LOG = LoggerFactory.getLogger(Drummer.class);
    @ConfigProperty(name = "player.name")
    String playerName;
    @ConfigProperty(name = "audioFolder")
    String audioFolder;
    @RestClient
    ConductorRestClient conductorRestClient;
    @Inject
    DrumPatternRepository drumPatternRepository;
    Musician musician;
    void onStart(@Observes StartupEvent ev) throws Exception {
        String instrument = conductorRestClient.getInstrument();
        LOG.info("Got instrument {}", instrument);

        DrumPattern pattern = drumPatternRepository.findByName(instrument);
        LOG.info("Will play pattern {}", pattern.pattern);

        URI uri = new URI("ws://localhost:8080/beat/" + playerName);
        ContainerProvider.getWebSocketContainer()
                .connectToServer(this, uri);
        musician = new Musician(instrument, pattern.pattern, audioFolder);
    }

    @OnMessage
    void message(String beatMessage) {
        musician.play(Short.valueOf(beatMessage));
    }
}
