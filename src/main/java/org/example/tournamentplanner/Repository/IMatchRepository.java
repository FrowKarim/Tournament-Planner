package org.example.tournamentplanner.Repository;

import org.example.tournamentplanner.Entities.Match;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IMatchRepository extends JpaRepository<Match, UUID> {


}
