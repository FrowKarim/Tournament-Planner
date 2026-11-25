package org.example.tournamentplanner.Repository;

import org.example.tournamentplanner.Entities.Match;
import org.springframework.data.jpa.repository.JpaRepository;

public interface matchRepository extends JpaRepository<Match, Long> {


}
