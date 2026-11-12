package org.example.tournamentplanner.Service;

import org.example.tournamentplanner.Entities.Player;
import org.example.tournamentplanner.Repository.IPlayerRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PlayerService {

    private IPlayerRepository playerRepository;

    public PlayerService(IPlayerRepository repository) {
        this.playerRepository = repository;
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player getPlayerById(Long id) {
        return playerRepository.findById(id).orElse(null);
    }

    public Player save(Player player) {
        return playerRepository.save(player);
    }

    public void deletePlayer(Long id) {  playerRepository.deleteById(id); }
}

//package org.example.tournamentplanner.Service;
//
//import org.example.tournamentplanner.Entities.Player;
//import org.example.tournamentplanner.Repository.IPlayerRepository;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.UUID;
//
//@Service
//public class PlayerService {
//
//    private IPlayerRepository playerRepository;
//
//    public PlayerService(IPlayerRepository repository) {
//        this.playerRepository = repository;
//    }
//
//    public List<Player> getAllPlayers() {
//        return playerRepository.findAll();
//    }
//
//    public Player getPlayerById(Long id) {
//        return playerRepository.findById(id).orElse(null);
//    }
//
//    public Player save(Player player) {
//        return playerRepository.save(player);
//    }
//
//    public void deletePlayer(Long id) {
//        playerRepository.deleteById(id);
//    }
//}
