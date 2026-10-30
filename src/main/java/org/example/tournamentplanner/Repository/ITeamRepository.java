package org.example.tournamentplanner.Repository;

import org.example.tournamentplanner.Entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ITeamRepository extends JpaRepository<Team, UUID> {


}
