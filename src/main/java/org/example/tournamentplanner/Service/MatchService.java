package org.example.tournamentplanner.Service;

import org.example.tournamentplanner.Entities.Match;
import org.example.tournamentplanner.Repository.matchRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class MatchService {

    private final matchRepository matchRepository;
    private final SimpMessagingTemplate template;

    public MatchService(matchRepository repository, SimpMessagingTemplate template) {
        this.matchRepository = repository;
        this.template = template;
    }

    public Match createMatch() {
        Match match = new Match();
        match.setHomeScore(0);
        match.setAwayScore(0);
        match.setStartTime(LocalDateTime.now());

        Match saved = matchRepository.save(match);
        publish(saved);   // push to clients
        return saved;
    }

    public void publish(Match match) {
        template.convertAndSend("/topic/matches", match);
    }


}
