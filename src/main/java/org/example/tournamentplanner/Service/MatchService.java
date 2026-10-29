package org.example.tournamentplanner.Service;

import org.example.tournamentplanner.DTO.MatchDTO;
import org.example.tournamentplanner.Entities.Match;
import org.example.tournamentplanner.Repository.IMatchRepository;

import java.time.LocalDateTime;

public class MatchService {

    private IMatchRepository matchRepository;

    public MatchService(IMatchRepository repository) {

         this.matchRepository = repository ;

    }

    public Match createMatch ()
    {
        Match match = new Match();
        match.setHomeScore(0);
        match.setAwayScore(0);


        return matchRepository.save(match);
    }


}
