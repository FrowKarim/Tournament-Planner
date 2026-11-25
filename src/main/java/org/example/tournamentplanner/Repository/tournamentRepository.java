package org.example.tournamentplanner.Repository;

import org.example.tournamentplanner.Entities.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface tournamentRepository extends JpaRepository<Tournament, Long> {
    boolean existsByName(String name);
}
