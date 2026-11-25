package org.example.tournamentplanner.Service;

import lombok.RequiredArgsConstructor;
import org.example.tournamentplanner.Entities.Player;
import org.example.tournamentplanner.Entities.Team;
import org.example.tournamentplanner.Entities.Tournament;
import org.example.tournamentplanner.Entities.TournamentStatus;
import org.example.tournamentplanner.Repository.playerRepository;
import org.example.tournamentplanner.Repository.teamRepository;
import org.example.tournamentplanner.Repository.tournamentRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

@Service
@RequiredArgsConstructor
public class TournamentService {

    private final tournamentRepository tournamentRepository;
    private final playerRepository playerRepository;
    private final teamRepository teamRepository;

    public Tournament createTournament(Long playerId, Tournament request) {
        Player organizer = playerRepository.findById(playerId)
                .orElseThrow(() -> fail(HttpStatus.NOT_FOUND, "Player not found"));

        validate(request);
        if (tournamentRepository.existsByName(request.getName())) {
            throw fail(HttpStatus.CONFLICT, "Tournament name already exists");
        }

        Tournament tournament = new Tournament();
        copyFields(request, tournament);
        tournament.setOrganizer(organizer);
        return tournamentRepository.save(tournament);
    }

    @Transactional
    public Tournament updateTournament(Long tournamentId, Long requesterId, Tournament request) {
        Tournament tournament = getTournament(tournamentId);
        requireOrganizer(tournament, requesterId);
        requireNotStarted(tournament);
        validate(request);

        copyFields(request, tournament);
        return tournament;
    }

    @Transactional
    public Team addTeam(Long tournamentId, Long teamId, Long requesterId) {
        Tournament tournament = getTournament(tournamentId);
        Team team = getTeam(teamId);

        if (!team.getCaptain().getId().equals(requesterId)) {
            throw fail(HttpStatus.FORBIDDEN, "Only the team captain can join a tournament");
        }
        requireNotStarted(tournament);
        if (team.getTournament() != null) {
            throw fail(HttpStatus.CONFLICT, "Team is already in a tournament");
        }
        if (tournament.getMaxTeams() != null && tournament.getTeams().size() >= tournament.getMaxTeams()) {
            throw fail(HttpStatus.CONFLICT, "Tournament is full");
        }
        if (team.getPlayers().size() > tournament.getMaxTeamSize()) {
            throw fail(HttpStatus.CONFLICT, "Team is too big");
        }

        team.setTournament(tournament);
        return team;
    }

    @Transactional
    public void removeTeam(Long tournamentId, Long teamId, Long requesterId) {
        Tournament tournament = getTournament(tournamentId);
        requireOrganizer(tournament, requesterId);

        Team team = getTeam(teamId);
        if (!tournament.equals(team.getTournament())) {
            throw fail(HttpStatus.NOT_FOUND, "Team is not in this tournament");
        }

        team.setTournament(null);
    }

    public Tournament getTournament(Long id) {
        return tournamentRepository.findById(id)
                .orElseThrow(() -> fail(HttpStatus.NOT_FOUND, "Tournament not found"));
    }

    private Team getTeam(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> fail(HttpStatus.NOT_FOUND, "Team not found"));
    }

    private void requireOrganizer(Tournament tournament, Long requesterId) {
        if (tournament.getOrganizer() == null || !tournament.getOrganizer().getId().equals(requesterId)) {
            throw fail(HttpStatus.FORBIDDEN, "Only the organizer can do this");
        }
    }

    private void requireNotStarted(Tournament tournament) {
        if (tournament.getStatus() != TournamentStatus.CREATED) {
            throw fail(HttpStatus.CONFLICT, "Tournament already started");
        }
    }

    private void validate(Tournament tournament) {
        if (tournament.getName() == null || tournament.getName().isBlank()) {
            throw fail(HttpStatus.BAD_REQUEST, "Tournament name is required");
        }
        if (tournament.getMaxTeamSize() < 1) {
            throw fail(HttpStatus.BAD_REQUEST, "Team size must be at least 1");
        }
        if (tournament.getMaxTeams() != null && tournament.getMaxTeams() < 2) {
            throw fail(HttpStatus.BAD_REQUEST, "Need room for at least 2 teams");
        }
    }

    private void copyFields(Tournament from, Tournament to) {
        to.setName(from.getName());
        to.setGame(from.getGame());
        to.setStartDate(from.getStartDate());
        to.setEndDate(from.getEndDate());
        to.setMaxTeamSize(from.getMaxTeamSize());
        to.setMaxTeams(from.getMaxTeams());
    }

    private ResponseStatusException fail(HttpStatus status, String message) {
        return new ResponseStatusException(status, message);
    }

}