package org.example.tournamentplanner.Controller;
import org.example.tournamentplanner.DTO.team.CreateTeamDTO;
import org.example.tournamentplanner.Entities.Team;
import org.example.tournamentplanner.Repository.ITeamRepository;
import org.example.tournamentplanner.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Teams")
public class TeamController
{

    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping("/{id}")
    public Team getTeamByID(@PathVariable Long id)
    {
        return teamService.getTeam(id);
    }

    @PostMapping
    public Team create(@RequestParam Long playerId, @RequestBody Team team) {
        return teamService.createTeam(playerId, team);
    }

    @PostMapping("/{teamId}/players/{playerId}")
    public Team addPlayer(@PathVariable Long teamId, @PathVariable Long playerId,
                          @RequestParam Long requesterId) {
        return teamService.addPlayer(teamId, playerId, requesterId);
    }

    @DeleteMapping("/{teamId}/players/{playerId}")
    public Team removePlayer(@PathVariable Long teamId, @PathVariable Long playerId,
                             @RequestParam Long requesterId) {
        return teamService.removePlayer(teamId, playerId, requesterId);
    }

}