package org.example.tournamentplanner.Controller;

import org.example.tournamentplanner.Entities.Tournament;
import org.example.tournamentplanner.Service.TournamentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/Tournaments")
public class TournamentController
{

    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping("/{id}")
    public Tournament getTournamentbyID(@PathVariable Long id)
    {
        return tournamentService.getTournament(id);
    }

    @PostMapping
    public Tournament create(@RequestParam Long playerId, @RequestBody Tournament tournament) {
        return tournamentService.createTournament(playerId, tournament);
    }

}