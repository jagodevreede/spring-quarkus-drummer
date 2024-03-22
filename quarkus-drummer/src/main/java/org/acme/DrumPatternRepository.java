package org.acme;

import io.quarkus.hibernate.orm.panache.PanacheRepository;
import jakarta.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class DrumPatternRepository implements PanacheRepository<DrumPattern> {

    public DrumPattern findByName(String name) {
        return find("name", name).firstResult();
    }
}
