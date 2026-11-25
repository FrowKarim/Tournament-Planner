package org.example.tournamentplanner.Service;

import lombok.RequiredArgsConstructor;
import org.example.tournamentplanner.Entities.Player;
import org.example.tournamentplanner.Entities.Team;
import org.example.tournamentplanner.Entities.Tournament;
import org.example.tournamentplanner.Repository.playerRepository;
import org.example.tournamentplanner.Repository.teamRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final teamRepository teamRepository;
    private final playerRepository playerRepository;

<<<<<<< Updated upstream
    @Transactional
    public Team createTeam(Long captainId, Team team) {
        Player captain = getPlayer(captainId);
        team.setCaptain(captain);
        team.getPlayers().add(captain);
=======
    public Team createTeam(Long captainId, Team request) {
        if (request.getName() == null || request.getName().isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Team name is required");
        }
        Player captain = getPlayer(captainId);

        Team team = new Team();
        team.setName(request.getName());
        team.setCaptain(captain);
        team.setPlayers(new ArrayList<>(List.of(captain)));
>>>>>>> Stashed changes
        return teamRepository.save(team);
    }

    @Transactional
    public Team addPlayer(Long teamId, Long playerId, Long requesterId) {
        Team team = getTeam(teamId);
        requireCaptain(team, requesterId);

        if (team.getPlayers().stream().anyMatch(p -> p.getId().equals(playerId))) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Player already in team");
        }
        Tournament tournament = team.getTournament();
        if (tournament != null && team.getPlayers().size() >= tournament.getMaxTeamSize()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Team is full for this tournament");
        }

        team.getPlayers().add(getPlayer(playerId));
        return team;
    }

    @Transactional
    public Team removePlayer(Long teamId, Long playerId, Long requesterId) {
        Team team = getTeam(teamId);
        requireCaptain(team, requesterId);

        if (team.getCaptain().getId().equals(playerId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "The captain cannot be removed");
        }
        if (!team.getPlayers().removeIf(p -> p.getId().equals(playerId))) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Player is not in this team");
        }
        return team;
    }

    public Team getTeam(Long id) {
        return teamRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found: " + id));
    }

    private Player getPlayer(Long id) {
        return playerRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Player not found: " + id));
    }

    private void requireCaptain(Team team, Long requesterId) {
        if (!team.getCaptain().getId().equals(requesterId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Only the team captain can do this");
        }
    }
}