package org.example.tournamentplanner.Service;

import org.example.tournamentplanner.Entities.Player;
import org.example.tournamentplanner.Repository.playerRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class PlayerService {

    private playerRepository playerRepository;

    public PlayerService(playerRepository repository) {
        this.playerRepository = repository;
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll();
    }

    public Player getPlayerById(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found: " + id));
    }

    public Player save(Player player) {
        if (player.getFirstName() == null || player.getFirstName().isBlank()
                || player.getLastName() == null || player.getLastName().isBlank()
                || player.getEmail() == null || player.getEmail().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "First name, last name and email are required");
        }

        if (player.getId() == null && playerRepository.existsByEmail(player.getEmail())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already in use: " + player.getEmail());
        }
        if (player.getId() != null && !playerRepository.existsById(player.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found: " + player.getId());
        }

        return playerRepository.save(player);
    }

<<<<<<< Updated upstream
    public void deletePlayer(Long id) {  playerRepository.deleteById(id); }
}
=======
    public void deletePlayer(Long id) {
        if (!playerRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found: " + id);
        }

        try {
            playerRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT,
                    "Player is still a captain, organizer or team member and cannot be deleted");
        }
    }
}
>>>>>>> Stashed changes
