package org.example.tournamentplanner.Repository;

import org.example.tournamentplanner.Entities.Team;
import org.example.tournamentplanner.Entities.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface ITournamentRepository extends JpaRepository<Tournament, UUID> {
    boolean existsByName(String name);
}
