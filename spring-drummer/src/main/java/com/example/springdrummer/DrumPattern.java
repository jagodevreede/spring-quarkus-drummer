package com.example.springdrummer;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
public class DrumPattern {

    @Id
    @GeneratedValue
    public Long id;
    public String name;
    public String pattern;
}