package org.example.tournamentplanner.Repository;

import org.example.tournamentplanner.Entities.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IPlayerRepository extends JpaRepository<Player, UUID> {

}
