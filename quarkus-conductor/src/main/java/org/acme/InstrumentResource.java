package org.acme;

import io.quarkus.virtual.threads.VirtualThreads;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.ArrayList;
import java.util.List;

@Path("/instrument")
public class InstrumentResource {

    private static final List<String> ALL_INSTRUMENTS =
            List.of("ClHat-08", "Kick-08", "Flam-01");
    private final List<String> availableInstrument = new ArrayList<>();

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    @VirtualThreads
    public String getInstrument() {
        if (availableInstrument.isEmpty()) {
            availableInstrument.addAll(ALL_INSTRUMENTS);
        }
        return availableInstrument.remove(0);
    }

}
