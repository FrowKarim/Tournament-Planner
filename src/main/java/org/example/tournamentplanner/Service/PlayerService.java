package org.example.tournamentplanner.Service;

import org.example.tournamentplanner.Entities.Player;
import org.example.tournamentplanner.Repository.IPlayerRepository;

import java.util.List;
import java.util.UUID;

public class PlayerService {

    private IPlayerRepository playerRepository;

    public PlayerService(IPlayerRepository repository) {
        this.playerRepository = repository;
    }

    public List<Player> getPlayersByIds(List<UUID> ids) {
        return playerRepository.findAllById(ids);
    }
}
