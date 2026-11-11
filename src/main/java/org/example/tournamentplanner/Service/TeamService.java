package org.example.tournamentplanner.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.example.tournamentplanner.DTO.team.CreateTeamDTO;
import org.example.tournamentplanner.Entities.Player;
import org.example.tournamentplanner.Entities.Team;
import org.example.tournamentplanner.Entities.Tournament;
import org.example.tournamentplanner.Repository.IPlayerRepository;
import org.example.tournamentplanner.Repository.ITeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final ITeamRepository teamRepository;
    private final IPlayerRepository playerRepository;

    @Transactional
    public Team createTeam(Long captainId, Team team) {
        Player captain = getPlayer(captainId);
        team.setCaptain(captain);
        team.getPlayers().add(captain);
        return teamRepository.save(team);
    }

    @Transactional
    public Team addPlayer(Long teamId, Long playerId, Long requesterId) {
        Team team = getTeam(teamId);

        if (!team.getCaptain().getId().equals(requesterId)) {
            throw new IllegalStateException("Only the team captain can add players");
        }
        if (team.getPlayers().stream().anyMatch(p -> p.getId().equals(playerId))) {
            throw new IllegalStateException("Player already in team");
        }
        Tournament tournament = team.getTournament();
        if (tournament != null && team.getPlayers().size() >= tournament.getMaxTeamSize()) {
            throw new IllegalStateException("Team is full for this tournament");
        }

        team.getPlayers().add(getPlayer(playerId));
        return team;
    }

    @Transactional
    public Team removePlayer(Long teamId, Long playerId, Long requesterId) {
        Team team = getTeam(teamId);

        if (!team.getCaptain().getId().equals(requesterId)) {
            throw new IllegalStateException("Only the team captain can remove players");
        }
        if (team.getCaptain().getId().equals(playerId)) {
            throw new IllegalStateException("The captain cannot be removed");
        }

        team.getPlayers().removeIf(p -> p.getId().equals(playerId));
        return team;
    }

    public Team getTeam(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Team not found"));
    }

    private Player getPlayer(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));
    }
}