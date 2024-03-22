package com.example.springdrummer;


import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class ConductorRestClient {

    @Value("${instrument-url}")
    private URI instrumentUri;
    private final RestClient client = RestClient.create();

    public String getInstrument() {
        return client.get()
                .uri(instrumentUri)
                .retrieve()
                .toEntity(String.class)
                .getBody();
    }
}