package org.example.tournamentplanner.Service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.example.tournamentplanner.DTO.team.CreateTeamDTO;
import org.example.tournamentplanner.Entities.Player;
import org.example.tournamentplanner.Entities.Team;
import org.example.tournamentplanner.Repository.IPlayerRepository;
import org.example.tournamentplanner.Repository.ITeamRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class TeamService {

@Autowired
 private ITeamRepository teamRepository;


    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private IPlayerRepository playerRepository;

    public Team createTeam (CreateTeamDTO DTO) {
        Team team = new Team();

        team.setName(DTO.getTeamName());

        List<Player> players = playerRepository.findAllById(DTO.getPlayerIDs());


//        List<Player> playerReferences = DTO.getPlayerIDs()
//                .stream()
//                .map(playerId -> entityManager.getReference(Player.class, playerId)) // Create a reference
//                .collect(Collectors.toList());
//        team.setPlayers(playerReferences);

        return team;
    }


}
