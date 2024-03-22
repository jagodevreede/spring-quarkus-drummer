package org.acme;

import io.quarkus.scheduler.Scheduled;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

@ApplicationScoped
public class Conductor {
    private static final short BPM = 180;
    private static final int MS_IN_MINUTE = 60_000;

    @Inject
    BeatWebSocket beatWebSocket;

    @Scheduled(every = "15s")
    public void startRun() {
        int beatsPlayed = -1;
        long currentTimeMillis = System.currentTimeMillis();
        long nextBeatTime = currentTimeMillis + (MS_IN_MINUTE / BPM);
        while (++beatsPlayed < 32) {
            waitUntil(nextBeatTime);
            beatWebSocket.broadcast(Integer.toString(beatsPlayed % 8 + 1));
            nextBeatTime = nextBeatTime + (MS_IN_MINUTE / BPM);
        }
    }

    private static void waitUntil(long nextBeatTime) {
        while (System.currentTimeMillis() < nextBeatTime) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
