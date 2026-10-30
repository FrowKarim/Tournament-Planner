package org.example.tournamentplanner.Controller;
import org.example.tournamentplanner.Entities.Match;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/match")
public class MatchController {

    @GetMapping
    public Match getMatch(@RequestParam UUID matchID) {

        return  getMatch(matchID);
    }


}


