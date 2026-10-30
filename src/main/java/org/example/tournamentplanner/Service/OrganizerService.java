package org.example.tournamentplanner.Service;

import org.example.tournamentplanner.Entities.Match;
import org.example.tournamentplanner.Entities.Organizer;
import org.example.tournamentplanner.Entities.Player;
import org.example.tournamentplanner.Entities.Tournament;
import org.example.tournamentplanner.Repository.IMatchRepository;
import org.example.tournamentplanner.Repository.IOrganizerRepository;
import org.example.tournamentplanner.Repository.ITournamentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class OrganizerService {

    private final IOrganizerRepository organizerRepository;
    private final ITournamentRepository tournamentRepository;

    public OrganizerService(IOrganizerRepository organizerRepository,
                            ITournamentRepository tournamentRepository) {
        this.organizerRepository = organizerRepository;
        this.tournamentRepository = tournamentRepository;
    }

    public Organizer createOrganizer(Player player)
    {
        Organizer organizer = new Organizer();
        organizer.setEmail(player.getEmail());
        organizer.setName(player.getFirstName());

        return organizerRepository.save(organizer);
    }


    public Tournament createTournament(UUID organizerId, String name,
                                       LocalDate startDate, LocalDate endDate) {

        Organizer organizer = organizerRepository.findById(organizerId)
                .orElseThrow(() -> new IllegalArgumentException("Organizer not found: " + organizerId));

        if (tournamentRepository.existsByName(name)) {
            throw new IllegalArgumentException("Tournament name already exists: " + name);
        }
        if (startDate != null && endDate != null && endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        Tournament tournament = new Tournament();
        tournament.setName(name);
        tournament.setStartDate(startDate);
        tournament.setEndDate(endDate);
        tournament.setOrganizer(organizer);

        return tournamentRepository.save(tournament);
    }

}
