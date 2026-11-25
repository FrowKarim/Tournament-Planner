package org.example.tournamentplanner.Repository;

import org.example.tournamentplanner.Entities.Team;
import org.springframework.data.jpa.repository.JpaRepository;

public interface teamRepository extends JpaRepository<Team, Long> {


}
