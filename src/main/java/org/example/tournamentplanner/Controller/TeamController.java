package org.example.tournamentplanner.Controller;
import org.example.tournamentplanner.DTO.team.CreateTeamDTO;
import org.example.tournamentplanner.Entities.Team;
import org.example.tournamentplanner.Repository.ITeamRepository;
import org.example.tournamentplanner.Service.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("api/teams")
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping
    public Team createTeam(@RequestBody CreateTeamDTO team) {

        te
    }

}
