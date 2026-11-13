package org.example.tournamentplanner.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tournamentplanner.Entities.Player;
import org.example.tournamentplanner.Entities.Team;
import org.example.tournamentplanner.Entities.Tournament;
import org.example.tournamentplanner.Entities.TournamentStatus;
import org.example.tournamentplanner.Repository.IPlayerRepository;
import org.example.tournamentplanner.Repository.ITeamRepository;
import org.example.tournamentplanner.Repository.ITournamentRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TournamentService {

    private final ITournamentRepository tournamentRepository;
    private final IPlayerRepository playerRepository;
    private final ITeamRepository teamRepository;

    @Transactional
    public Tournament createTournament(Long playerId, Tournament tournament) {
        Player organizer = playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));

        if (tournamentRepository.existsByName(tournament.getName())) {
            throw new IllegalArgumentException("Tournament name already exists");
        }

        tournament.setOrganizer(organizer);
        return tournamentRepository.save(tournament);
    }

    @Transactional
    public Team addTeam(Long tournamentId, Long teamId, Long requesterId) {
        Tournament tournament = getTournament(tournamentId);
        Team team = getTeam(teamId);

        if (!team.getCaptain().getId().equals(requesterId)) {
            throw new IllegalStateException("Only the team captain can join a tournament");
        }
        if (tournament.getStatus() != TournamentStatus.CREATED) {
            throw new IllegalStateException("Tournament already started");
        }
        if (tournament.getMaxTeams() != null && tournament.getTeams().size() >= tournament.getMaxTeams()) {
            throw new IllegalStateException("Tournament is full");
        }
        if (team.getPlayers().size() > tournament.getMaxTeamSize()) {
            throw new IllegalStateException("Team is too big");
        }

        team.setTournament(tournament);
        return team;
    }

    @Transactional
    public void removeTeam(Long tournamentId, Long teamId, Long requesterId) {
        Tournament tournament = getTournament(tournamentId);

        if (!tournament.getOrganizer().getId().equals(requesterId)) {
            throw new IllegalStateException("Only the organizer can remove teams");
        }

        getTeam(teamId).setTournament(null);
    }

    public Tournament getTournament(Long id) {
        return tournamentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Tournament not found"));
    }

    private Team getTeam(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Team not found"));
    }

    public Tournament updateTournament(Long playerId, Tournament tournament) {
        return tournamentRepository.save(tournament);
    }
}