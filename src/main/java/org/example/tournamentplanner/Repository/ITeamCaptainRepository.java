package org.example.tournamentplanner.Repository;

import org.example.tournamentplanner.Entities.TeamCaptain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ITeamCaptainRepository extends JpaRepository<TeamCaptain, UUID> {
}
