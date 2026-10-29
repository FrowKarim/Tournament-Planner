package org.example.tournamentplanner.Service;

import org.example.tournamentplanner.Entities.Player;
import org.example.tournamentplanner.Repository.IMatchRepository;
import org.example.tournamentplanner.Repository.IPlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;
import java.util.UUID;

public class PlayerService {

    @Autowired
    private IPlayerRepository playerRepository;

 public List<Player> GetPlayerByID(List<UUID> IDs) {


    return playerRepository.findAllById(IDs);
 }
}
