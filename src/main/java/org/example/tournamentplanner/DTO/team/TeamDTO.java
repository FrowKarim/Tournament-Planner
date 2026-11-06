package org.example.tournamentplanner.DTO.team;
import org.example.tournamentplanner.Entities.Team;

import java.time.LocalDate;
import java.util.UUID;

public record TeamDTO(Long id, String name, LocalDate startDate) {
}
