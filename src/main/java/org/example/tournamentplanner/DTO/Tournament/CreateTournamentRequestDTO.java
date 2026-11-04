package org.example.tournamentplanner.DTO.Tournament;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

public record CreateTournamentRequestDTO (String Name , UUID OrganizerId)
{

}
