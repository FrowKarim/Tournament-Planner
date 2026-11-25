package org.example.tournamentplanner.Repository;

import org.example.tournamentplanner.Entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface playerRepository extends JpaRepository<Player, Long> {

    boolean existsByEmail(String email);

}