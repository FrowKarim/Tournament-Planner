package org.example.tournamentplanner.Controller;
import org.example.tournamentplanner.Entities.Match;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/match")
public class MatchController {

    @GetMapping
    public Match getMatch(@RequestParam int matchID) {
        Match match = new Match();
    }


}


