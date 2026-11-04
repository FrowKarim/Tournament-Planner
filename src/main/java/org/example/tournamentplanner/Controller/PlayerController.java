package org.example.tournamentplanner.Controller;

import org.example.tournamentplanner.Entities.Player;
import org.example.tournamentplanner.Repository.IPlayerRepository;
import org.example.tournamentplanner.Service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/player")

public class PlayerController {

@Autowired
    private final PlayerService playerService;

    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

// change UUID to long because I can't use this in getrequests easily
    @GetMapping("/{id}")
    public Player getPlayer(@PathVariable UUID id) {

        return playerService.getPlayerById(id);
    }


@PostMapping
    public Player createPlayer(@RequestBody Player player) {
        player.getFirstName();

        return player;
    }
}

