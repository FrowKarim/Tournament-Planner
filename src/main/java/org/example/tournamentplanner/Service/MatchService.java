package org.example.tournamentplanner.Service;

import org.example.tournamentplanner.Entities.Match;
import org.example.tournamentplanner.Repository.matchRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MatchService {

    private matchRepository matchRepository;

    public MatchService(matchRepository repository) {

         this.matchRepository = repository ;

    }

    public Match createMatch ()
    {
        Match match = new Match();
        match.setHomeScore(0);
        match.setAwayScore(0);
        match.setStartTime(LocalDateTime.now());
//        match.setTournament();


        return matchRepository.save(match);
    }


}
