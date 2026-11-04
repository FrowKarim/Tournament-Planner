package org.example.tournamentplanner.Controller;

import org.example.tournamentplanner.Entities.Team;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/player")

public class PlayerController {

    @GetMapping
    public List<Team> getTeams() {



    }

}

