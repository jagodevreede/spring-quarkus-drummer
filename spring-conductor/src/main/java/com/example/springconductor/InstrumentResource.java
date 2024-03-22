package com.example.springconductor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("instrument")
public class InstrumentResource {
    private static final List<String> ALL_INSTRUMENTS =
            List.of("ClHat-08", "Kick-08", "Flam-01");
    private final List<String> availableInstrument = new ArrayList<>();

    @GetMapping()
    public String getInstrument() {
        if (availableInstrument.isEmpty()) {
            availableInstrument.addAll(ALL_INSTRUMENTS);
        }
        return availableInstrument.remove(0);
    }

}
