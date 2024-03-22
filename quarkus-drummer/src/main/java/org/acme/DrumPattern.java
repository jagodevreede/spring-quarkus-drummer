package org.acme;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class DrumPattern {
    @Id
    @GeneratedValue
    public Long id;
    public String name;
    public String pattern;
}
