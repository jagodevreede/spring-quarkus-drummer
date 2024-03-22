package com.example.springdrummer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DrumPatternRepository extends JpaRepository<DrumPattern, Long> {

    DrumPattern findByName(String name);
}
