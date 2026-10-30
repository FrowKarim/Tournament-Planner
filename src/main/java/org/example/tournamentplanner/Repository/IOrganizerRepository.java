package org.example.tournamentplanner.Repository;

import org.example.tournamentplanner.Entities.Match;
import org.example.tournamentplanner.Entities.Organizer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface IOrganizerRepository extends JpaRepository<Organizer, UUID> {

}
