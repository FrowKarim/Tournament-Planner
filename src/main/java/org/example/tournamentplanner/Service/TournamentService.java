package org.example.tournamentplanner.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.tournamentplanner.DTO.Tournament.CreateTournamentRequestDTO;
import org.example.tournamentplanner.Entities.Tournament;
import org.example.tournamentplanner.Repository.IMatchRepository;
import org.example.tournamentplanner.Repository.ITournamentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TournamentService {

    private ITournamentRepository tournamentRepository;

    public TournamentService(ITournamentRepository repository) {

        this.tournamentRepository = repository ;

    }

    @Transactional
    public Tournament createTournament(CreateTournamentRequestDTO request) {
        // Implementation not correct. Need a mapper or just skip DTO's entirely
        Tournament tournament = new Tournament(
          null,
                request.Name(),
                null,
                null,
                null,
                null,
                null
);
        return tournamentRepository.save(tournament);
    }


}
