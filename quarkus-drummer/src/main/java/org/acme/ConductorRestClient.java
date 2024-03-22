package org.acme;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/instrument")
@RegisterRestClient(configKey="conductor-api")
public interface ConductorRestClient {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String getInstrument();
}
