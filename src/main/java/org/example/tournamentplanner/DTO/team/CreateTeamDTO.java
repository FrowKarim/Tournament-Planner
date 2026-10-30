package org.example.tournamentplanner.DTO.team;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.tournamentplanner.Entities.Team;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CreateTeamDTO {
    private String TeamName;
    private int maxPlayers;
    private List<UUID> playerIDs;

}
