package org.example.tournamentplanner.DTO.Tournament;

import java.time.LocalDate;
import java.util.UUID;

// apparently the "record" part fully implements what I usually put in {}
public record TournamentDTO(UUID id, String name, LocalDate startDate) { }