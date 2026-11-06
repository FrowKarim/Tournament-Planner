package org.example.tournamentplanner.Controller;

import org.example.tournamentplanner.DTO.Tournament.CreateTournamentRequestDTO;
import org.example.tournamentplanner.Entities.Tournament;
import org.example.tournamentplanner.Service.TournamentService;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/Tournaments")
public class TournamentController
{

    private final TournamentService tournamentService;

    public TournamentController(TournamentService tournamentService) {
        this.tournamentService = tournamentService;
    }

    @GetMapping("/{id}")
    public Tournament getTournamentbyID(@RequestParam UUID ID)
    {
      return getTournamentbyID(ID) ;
    }

    @PostMapping
    public Tournament create(@RequestBody CreateTournamentRequestDTO request) {
        return tournamentService.createTournament(request);
    }



}
